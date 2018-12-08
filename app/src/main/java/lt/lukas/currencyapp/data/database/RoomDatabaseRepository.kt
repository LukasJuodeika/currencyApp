package lt.lukas.currencyapp.data.database

import androidx.lifecycle.Transformations
import io.reactivex.Maybe
import io.reactivex.Scheduler
import lt.lukas.currencyapp.data.objects.Rate
import androidx.lifecycle.LiveData



class RoomDatabaseRepository(
    private val scheduler: Scheduler,
    private val main: Scheduler,
    private val periodDao: PeriodDao,
    private val rateDao: RateDao
) {
    fun deleteAllData(afterInsert: (t: Unit) -> Unit) {
        Maybe.fromCallable {
            periodDao.deleteAllData()
            rateDao.deleteAllData()
        }
            .subscribeOn(scheduler)
            .observeOn(main)
            .subscribe(afterInsert)

    }


    fun getFullRates(): LiveData<ArrayList<Rate>>
    {
        var rateLiveData = rateDao.getRates()
        val outputLiveData = Transformations.map(rateLiveData
        ) {
            for(rate in it) {
                rate.periods = periodDao.getPeriodsByCodeSynchronous(rate.code) as ArrayList            }
            it as ArrayList
        }
        return outputLiveData
    }

//    fun insertPeriods(product: Product, afterInsert: (t: Unit) -> Unit) {
//        Maybe.fromCallable { productDao.insertSingleProduct(product) }
//            .subscribeOn(scheduler)
//            .observeOn(main)
//            .subscribe(afterInsert)
//    }



}