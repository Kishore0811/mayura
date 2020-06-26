package com.primemover.mayura.collection

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.primemover.mayura.R
import com.primemover.mayura.api.APIClient
import com.primemover.mayura.constants.Utils.TAG
import com.primemover.mayura.constants.Utils.toastMessage
import com.primemover.mayura.databinding.ActivityCollectionBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException

class CollectionActivity : AppCompatActivity() {
    lateinit var binding: ActivityCollectionBinding
    private lateinit var call: Call<CollectionResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("savedInstance", savedInstanceState.toString())

        binding = DataBindingUtil.setContentView(this, R.layout.activity_collection)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.collectionProgressBar.visibility = View.VISIBLE

        call = APIClient.instance.getcollection()

        call.enqueue(object : Callback<CollectionResponse> {

            override fun onFailure(call: Call<CollectionResponse>, t: Throwable) {
                Log.i("Failure", t.toString())

                binding.collectionProgressBar.visibility = View.GONE

                //Timeout Exception
                if (t is SocketTimeoutException) {

                    toastMessage(this@CollectionActivity, R.string.connection_out)

                    //Internet Turned Off
                } else {
                    toastMessage(this@CollectionActivity, R.string.internet_off)
                }
                if (call.isCanceled) {
                    onBackPressed()
                }
            }

            override fun onResponse(call: Call<CollectionResponse>, response: Response<CollectionResponse>) {
                binding.scrollView.visibility = View.VISIBLE

                val collection: CollectionResponse = response.body()!!
                setCollection(collection)

                binding.collectionProgressBar.visibility = View.GONE
                if (call.isCanceled) {
                    Log.i(TAG, "call is cancelled")
                    onBackPressed()
                }
            }

        })
    }

    private fun setCollection(collection: CollectionResponse) {

//      Log.i("Response", collection.toString())

        //Hp Details
        binding.newHpLabel.text = collection.hp.new
        binding.textOldHpLabel.text = collection.hp.old
        binding.textFinanceLabel.text = collection.hp.finance
        binding.totalHpLabel.text = collection.hp.total.toString()
        binding.totalAmountLabel.text = collection.hp.total_amount

        //Receipt Details
        binding.textPrincipalLabel.text = collection.receipt.principal
        binding.textInterestLabel.text = collection.receipt.interest
        binding.textPenaltyReceiptLabel.text = collection.receipt.penalty
        binding.textInterestLabel.text = collection.receipt.interest
        binding.textAccountLabel.text = collection.receipt.account
        binding.textTotalReceiptLabel.text = collection.receipt.total

        //PreCloser Details
        binding.textPenaltyLabel.text = collection.precloser.penalty
        binding.textDiscountLabel.text = collection.precloser.discount
        binding.textPaidAmountLabel.text = collection.precloser.paid_amount
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "Activity started")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "Activity Resumed")
    }

    override fun onBackPressed() {
        when {
            !call.isExecuted -> {
                call.cancel()

            }
            else -> {
                super.onBackPressed()
            }

        }

    }

}
