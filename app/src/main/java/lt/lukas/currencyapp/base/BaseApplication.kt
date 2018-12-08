package lt.lukas.currencyapp.base

import android.app.Application
import lt.lukas.currencyapp.di.AppModule
import org.koin.android.ext.android.startKoin

class BaseApplication : Application()
{
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(AppModule.appModule))
    }
}