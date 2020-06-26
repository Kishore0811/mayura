package com.primemover.mayura.constants

import android.content.Context
import com.primemover.mayura.login.LoginResponse

class SharedPrefManager constructor(private val mCtx: Context) {

    val isLoggedIn: Boolean
        get() {

            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getString("token", null) != null
        }

    fun saveUser(user: LoginResponse) {

        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("user_id", user.user_id)
        editor.putString("user_name", user.user_name)
        editor.putString("user_type", user.user_type)
        editor.putString("token", user.token)
        editor.apply()
    }

    fun clear() {

        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    companion object {

        const val SHARED_PREF_NAME = "login_Pref"
        private var mInstance: SharedPrefManager? = null

        @Synchronized
        fun getInstance(mCtx: Context): SharedPrefManager {

            if (mInstance == null) {
                mInstance = SharedPrefManager(mCtx)
            }
            return mInstance as SharedPrefManager
        }
    }

}