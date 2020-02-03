package com.primemover.mayura.activities

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.primemover.mayura.R
import com.primemover.mayura.constants.SharedPrefManager
import com.primemover.mayura.databinding.ActivityHomeBinding
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        setSupportActionBar(binding.toolbar)
        binding.pendinglist.setOnClickListener(this)
        binding.collection.setOnClickListener(this)
        val str = ("Mayura Finance \n1518/2,\nvenkittapuram(signal),\nThadagam Main Rd,\nnear avila convent,\nCoimbatore-641025")
        address.setText(str)

//        val crashButton = Button(this)
//        crashButton.text = "Crash!"
//        crashButton.setOnClickListener {
//            throw RuntimeException("Test Crash") // Force a crash
//        }
//        addContentView(crashButton, ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT))
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.pendinglist -> {
                val intent = Intent(this, PendlinglistActivity::class.java)
                startActivity(intent)
            }

            R.id.collection -> {
                Toast.makeText(this, "Yet to implement", Toast.LENGTH_SHORT).show()
//                val i = Intent(this, CollectionActivity::class.java)
//                startActivity(i)
            }

        }

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout_menu -> {
                Log.i("TAG", "Logout button pressed")
                logoutAccount()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun logoutAccount() {
        val mDialog = Dialog(this)

        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mDialog.setContentView(R.layout.logout_dialog)
        mDialog.setCanceledOnTouchOutside(true)

        if (mDialog.window != null) {
            mDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mDialog.window!!.setLayout(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
            )
        }

        val cancel = mDialog.findViewById<TextView>(R.id.cancel)
        val ok = mDialog.findViewById<TextView>(R.id.ok)

        cancel.setOnClickListener { mDialog.dismiss() }

        ok.setOnClickListener {
            mDialog.dismiss()

            Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show()
            SharedPrefManager.getInstance(mCtx = this).clear()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        mDialog.show()

    }
}
