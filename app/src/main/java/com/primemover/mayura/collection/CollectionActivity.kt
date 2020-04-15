package com.primemover.mayura.collection

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.primemover.mayura.R
import com.primemover.mayura.api.APIClient
import com.primemover.mayura.constants.Utils.toastMessage
import com.primemover.mayura.databinding.ActivityCollectionBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException

class CollectionActivity : AppCompatActivity() {
    lateinit var binding: ActivityCollectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_collection)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.collectionProgressBar.visibility = View.VISIBLE

        APIClient.instance.getcollection().enqueue(object : Callback<CollectionResponse> {

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
            }

            override fun onResponse(call: Call<CollectionResponse>, response: Response<CollectionResponse>) {

                binding.scrollView.visibility = View.VISIBLE

                val collection: CollectionResponse = response.body()!!
                setCollection(collection)

                binding.collectionProgressBar.visibility = View.GONE
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
        binding.textPenaltyLabel.text = collection.precloser.penalty.toString()
        binding.textDiscountLabel.text = collection.precloser.discount.toString()
        binding.textPaidAmountLabel.text = collection.precloser.paid_amount.toString()
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
