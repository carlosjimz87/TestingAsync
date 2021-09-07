package com.onthespot.testingasync

import android.content.Intent
import android.os.Parcel
import android.os.Parcelable

class LoginResponse(
    var isLoggedIn: Boolean
) : Parcelable{

    constructor(parcel: Parcel) : this(parcel.readByte() != 0.toByte()) {
    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeByte((if (isLoggedIn) 1 else 0).toByte())
    }

    companion object CREATOR : Parcelable.Creator<LoginResponse> {
        private val LOGIN_RESPONSE_KEY = "LOGIN_RESPONSE_KEY"

        fun putLoginResponse(intent:Intent, isLoggedIn: Boolean){
            intent.putExtra(LOGIN_RESPONSE_KEY, LoginResponse(isLoggedIn))
        }

        fun getLoginResponse(intent: Intent): LoginResponse {
            return intent.getParcelableExtra(LOGIN_RESPONSE_KEY)!!
        }

        override fun createFromParcel(parcel: Parcel): LoginResponse {
            return LoginResponse(parcel)
        }

        override fun newArray(size: Int): Array<LoginResponse?> {
            return arrayOfNulls(size)
        }
    }

}