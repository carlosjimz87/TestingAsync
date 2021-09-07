package com.onthespot.testingasync

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MainActivityTest{

    private lateinit var callback: LoginTestCallback

    @Test
    fun setLoginCallback(loginCallback: LoginTestCallback){
        callback = loginCallback
    }

    interface LoginTestCallback{
        fun onHandleResponseCalled(loginResponse: LoginResponse)
    }


}