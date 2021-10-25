package com.onthespot.testingasync

import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import androidx.work.Configuration
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import androidx.work.workDataOf
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Test
import kotlin.jvm.Throws

const val KEY_1 ="KEY1"
const val KEY_2 ="KEY2"

class WorkersTests {
    private var context: Context = ApplicationProvider.getApplicationContext()

    @Before
    fun setUp() {
        val config = Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setExecutor(SynchronousExecutor())
            .build()

        // Initialize WorkManager for instrumentation tests.
        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)
    }

    @Test
    @Throws(Exception::class)
    fun testWorker(){
        val input = workDataOf(KEY_1 to 1, KEY_2 to 2)

        val request = OneTimeWorkRequestBuilder<TestWorker>()
            .setInputData(input)
            .build()

        val workManager = WorkManager.getInstance(context)

        workManager.enqueue(request).result.get()

        val workInfo = workManager.getWorkInfoById(request.id).get()
        val outputData = workInfo.outputData

        MatcherAssert.assertThat(workInfo.state, Matchers.`is`(WorkInfo.State.SUCCEEDED))
        MatcherAssert.assertThat(outputData, Matchers.`is`(input))
    }

    @Test
    @Throws(Exception::class)
    fun testWorkerNoInput() {
        // Create request
        val request = OneTimeWorkRequestBuilder<TestWorker>()
            .build()

        val workManager = WorkManager.getInstance(context)

        workManager.enqueue(request).result.get()
        val workInfo = workManager.getWorkInfoById(request.id).get()

        MatcherAssert.assertThat(workInfo.state, Matchers.`is`(WorkInfo.State.FAILED))
    }
}