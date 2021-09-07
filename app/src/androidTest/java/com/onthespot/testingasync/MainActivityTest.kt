package com.onthespot.testingasync

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MainActivityTest : MainActivity() {

    lateinit var mCallback: LoginTestCallback

    fun setLoginCallback(loginCallback: LoginTestCallback) {
        mCallback = loginCallback
    }

    interface LoginTestCallback {
        fun onHandleResponseCalled(loginResponse: LoginResponse?)
    }



}