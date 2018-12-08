package lt.lukas.currencyapp.di

import androidx.room.Room
import lt.lukas.currencyapp.data.database.CurrencyDatabase
import lt.lukas.currencyapp.data.ws.WebService
import lt.lukas.currencyapp.repository.MainRepository
import lt.lukas.currencyapp.ui.main.MainViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object AppModule
{

    val appModule = module {


        val db by lazy {
            Room
                .databaseBuilder(get(), CurrencyDatabase::class.java, "currency_db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
        }

        val retrofit by lazy{
            retrofit2.Retrofit.Builder()
                .baseUrl("https://jsonvat.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(WebService::class.java)
        }




        single{ MainRepository(db.ratesDao(), db.periodDao(),retrofit)}

        viewModel{MainViewModel(get())}
    }
}