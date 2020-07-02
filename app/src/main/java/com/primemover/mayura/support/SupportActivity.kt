package com.primemover.mayura.support

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.primemover.mayura.R
import com.primemover.mayura.constants.Utils.getVersionName
import com.primemover.mayura.databinding.ActivitySupportBinding

class SupportActivity : AppCompatActivity() {
    lateinit var binding: ActivitySupportBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_support)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val versionName = getString(R.string.version) + getVersionName()
        binding.versionTextView.text = versionName
        Log.i("Version Name", versionName)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
