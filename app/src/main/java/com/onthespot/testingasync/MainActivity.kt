package com.onthespot.testingasync

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import kotlinx.android.synthetic.main.activity_main.*

open class MainActivity : AppCompatActivity() {

    private val LOGIN_RECEIVER_FILTER = "LOGIN_RECEIVER_FILTER"

    private lateinit var broadCastReceiver: BroadcastReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginBtn.setOnClickListener {
            val user = userEB.text.toString()
            val pass = passEB.text.toString()

            performLoginViaService(user, pass)
        }
    }

    private fun performLoginViaService(user: String, pass: String) {

        broadCastReceiver = object : BroadcastReceiver() {
            override fun onReceive(contxt: Context?, intent: Intent?) {
                unregisterReceiver(this)

                val response = LoginResponse.getLoginResponse(intent!!)
                handleLoginResponse(response)
            }
        }
        registerReceiver(broadCastReceiver, IntentFilter(LOGIN_RECEIVER_FILTER))
        LoginService.performAsyncLogin(this, LOGIN_RECEIVER_FILTER, user, pass)
    }

    @VisibleForTesting
    private fun handleLoginResponse(response: LoginResponse) {
        Log.d("Handling response", "resp: $response")
        if (response.isLoggedIn) {
            Log.d("LoginResponse", "User logged in successfully")
        } else {
            Log.d("LoginResponse", "User logged in successfully")
        }
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadCastReceiver)
    }
}