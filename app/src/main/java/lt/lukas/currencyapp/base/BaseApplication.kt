package lt.lukas.currencyapp.base

import android.app.Application
import androidx.work.*
import lt.lukas.currencyapp.di.AppModule
import lt.lukas.currencyapp.workers.DownloadWorker
import org.koin.android.ext.android.startKoin
import java.util.concurrent.TimeUnit

class BaseApplication : Application()
{
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(AppModule.appModule))
        createWorkManager()
    }

    fun createWorkManager()
    {
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val build = PeriodicWorkRequest.Builder(DownloadWorker::class.java, 16, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork("Download", ExistingPeriodicWorkPolicy.KEEP, build)

    }
}