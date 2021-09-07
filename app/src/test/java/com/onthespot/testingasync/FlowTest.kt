package com.onthespot.testingasync



import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class FlowTest  {

    @ExperimentalCoroutinesApi
    @Test
    fun testFlow() = runBlockingTest{
        val flow = flow {
            emit(1)
            emit(2)
        }

        assertEquals(listOf(1,2),flow.toList())
    }

}