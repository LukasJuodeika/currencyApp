package lt.lukas.currencyapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import lt.lukas.currencyapp.data.database.PeriodDao
import lt.lukas.currencyapp.data.database.RateDao
import lt.lukas.currencyapp.data.objects.Rate
import lt.lukas.currencyapp.data.ws.WebService


class MainRepository(
    private val rateDao: RateDao,
    private val periodDao: PeriodDao,
    private val api: WebService
){


    fun parseNewData(): Disposable
    {
        return api.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({
                for(rate in it.rates)
                {
                    rateDao.insertRate(rate)
                    for(period in rate.periods)
                    {
                        period.rateCode = rate.code
                        periodDao.insertPeriod(period)
                    }
                }
            },{
                Log.d("parseNewData",it.message)
            })
    }



    fun getFullRates(): LiveData<ArrayList<Rate>>
    {
        val rateLiveData = rateDao.getRates()
        return Transformations.map(rateLiveData
        ) {
            for(rate in it) {
                rate.periods = periodDao.getPeriodsByCodeSynchronous(rate.code) as ArrayList            }
            it as ArrayList
        }
    }
}