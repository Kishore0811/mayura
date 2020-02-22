package com.primemover.mayura.collection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.primemover.mayura.R
import com.primemover.mayura.databinding.ActivityCollectionBinding

class CollectionActivity : AppCompatActivity() {
    lateinit var binding: ActivityCollectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_collection)
    }
}
