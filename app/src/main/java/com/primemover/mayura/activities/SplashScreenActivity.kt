package com.primemover.mayura.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.primemover.mayura.R
import com.primemover.mayura.constants.SharedPrefManager.Companion.SHARED_PREF_NAME

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        mContext = this
        val handler = Handler()
        handler.postDelayed({
            this.mRedirect()
        }, 3000)

    }

    private fun mRedirect() {
        val sp = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE )
        val token = sp.getString("token", "")

        if (token != null && token != "") {
            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(mContext, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}
