package com.cs4520.assignment1

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.lang.Exception

class APIWorkRequest(
    appContext: Context,
    workerParams: WorkerParameters,
) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
        try {
            Repository.fetchProducts(random = true)
        } catch (e: Exception) {
            Log.d("APIWorkManager", "Failed to load products", e)
            return Result.failure()
        }
        // Indicate whether the work finished successfully with the Result
        return Result.success()
    }
}
