package com.onthespot.testingasync

import android.app.IntentService
import android.content.Context
import android.content.Intent
import java.lang.RuntimeException

class LoginService(name: String) : IntentService(name) {

    companion object {
        fun performAsyncLogin(
            context: Context,
            responseBroadcastFilter: String,
            user: String,
            pass: String
        ) {

            Intent(context, LoginService::class.java).apply {
                action = LOGIN_ACTION
                putExtra(RESPONSE_BROADCAST_FILTER, responseBroadcastFilter)
            }.also {
                context.startService(it)
            }

        }

        const val LOGIN_ACTION = "LOGIN_ACTION"
        const val RESPONSE_BROADCAST_FILTER = "RESPONSE_BROADCAST_FILTER"
    }


    override fun onHandleIntent(intent: Intent?) {

        when(intent?.action){
            LOGIN_ACTION -> {
                performLogin(intent)

            }
            else -> throw RuntimeException("no handled action (${intent?.action})")
        }
    }

    fun performLogin(intent: Intent){
        try {
            Thread.sleep(2000)
            sendLoginresponse(true,intent.getStringExtra(RESPONSE_BROADCAST_FILTER))
        }
        catch (e: InterruptedException){
            e.printStackTrace()
        }
    }

    private fun sendLoginresponse(loggedSuccessfully: Boolean, responseBroadcastFilter: String?){
        val intent = Intent()
        LoginResponse.putLoginResponse(intent,loggedSuccessfully)
        intent.action = responseBroadcastFilter
        sendBroadcast(intent)
    }

}