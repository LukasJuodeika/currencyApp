package lt.lukas.currencyapp.workers

import android.content.Context
import android.util.Log
import androidx.work.*

class DownloadWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams)
{

    override fun doWork(): Result {
        Log.d("WorkManager","periodic work")
        return Result.success()
    }

}