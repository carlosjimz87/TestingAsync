package com.onthespot.testingasync

import android.util.Log
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule
import java.util.Objects
import kotlin.math.log


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TestAsyncLogin {

    private val TAG: String? = TestAsyncLogin::class.java.simpleName

    @Rule
    var mActivityRule = ActivityScenarioRule(MainActivityTest::class.java)


    @Test
    fun testViaLoginService() {

        onView(withId(R.id.userEB)).perform(typeText("User"))
        onView(withId(R.id.passEB)).perform(typeText("Pass"))

        val syncObj= Object()

        mActivityRule.scenario.onActivity {
            it.setLoginCallback( object: MainActivityTest.LoginTestCallback{
                override fun onHandleResponseCalled(loginResponse: LoginResponse?) {
                    Log.v(TAG,"onHandleResponse in thread ${Thread.currentThread().id}")

                    assertTrue(loginResponse!!.isLoggedIn)
                    synchronized(syncObj){
                        syncObj.notify()
                    }

                }
            })
        }

        onView(withId(R.id.loginBtn)).perform(click())

        synchronized(syncObj){
            syncObj.wait()
        }

    }
}