package com.onthespot.testingasync

import android.annotation.SuppressLint
import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class TestWorker(context: Context, parameters: WorkerParameters)
    : Worker(context, parameters) {
    @SuppressLint("RestrictedApi", "VisibleForTests")
    override fun doWork(): Result {
        return when(inputData.size()) {
            0 -> Result.failure()
            else -> Result.success(inputData)
        }
    }
}