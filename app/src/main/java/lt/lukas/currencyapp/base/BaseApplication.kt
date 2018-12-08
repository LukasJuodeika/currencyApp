package lt.lukas.currencyapp.base

import android.app.Application
import lt.lukas.currencyapp.AppModule
import org.koin.android.ext.android.startKoin

class BaseApplication : Application()
{
    lateinit var dependencyRetriever: DependencyRetriever private set
    override fun onCreate() {
        super.onCreate()
        dependencyRetriever = DependencyRetriever(this)

        startKoin(this, listOf(AppModule.appModule))
    }
}