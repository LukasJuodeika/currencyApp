package lt.lukas.currencyapp.base

import android.content.Context
import androidx.room.Room
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import lt.lukas.currencyapp.data.database.CurrencyDatabase
import lt.lukas.currencyapp.data.database.RoomDatabaseRepository
import lt.lukas.currencyapp.data.ws.WebService
import lt.lukas.currencyapp.model.MainRepository
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class DependencyRetriever(private val context: Context)
{
    private val db by lazy {
        Room
            .databaseBuilder(context, CurrencyDatabase::class.java, "currency_db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    val mainRepository: MainRepository by lazy {
        MainRepository(db.ratesDao(),db.periodDao(),retrofit)
    }

    private val retrofit by lazy{
        retrofit2.Retrofit.Builder()
            .baseUrl("https://jsonvat.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(WebService::class.java)
    }

    val roomDatabaseRepository by lazy {
        RoomDatabaseRepository(
            Schedulers.io(),
            AndroidSchedulers.mainThread(),
            db.periodDao(),
            db.ratesDao())
    }
}
val Context.dependencyRetriever: DependencyRetriever get() =
    (applicationContext as BaseApplication).dependencyRetriever