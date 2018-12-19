package lt.lukas.currencyapp.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import lt.lukas.currencyapp.base.BaseApplication
import lt.lukas.currencyapp.repository.MainRepository
import org.koin.android.ext.android.getKoin

class DownloadWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams)
{

    override fun doWork(): Result {
        Log.d("WorkManager","periodic work")

        val koin = (applicationContext as BaseApplication).getKoin()
        val mainRepository = koin.get<MainRepository>()
        mainRepository.parseNewData()

        return Result.success()
    }

}