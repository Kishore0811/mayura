package com.primemover.mayura.hpdetails

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.primemover.mayura.R
import com.primemover.mayura.api.APIClient
import com.primemover.mayura.constants.Utils.imageView
import com.primemover.mayura.constants.Utils.toastMessage
import com.primemover.mayura.databinding.ActivityHpdetailsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException

class HpDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityHpdetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_hpdetails)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val hpId = intent.getStringExtra("hpId")
        Log.i("Hpid", hpId!!.toString())

        APIClient.instance.hpdetails(hpId).enqueue(object : Callback<ArrayList<HpDetailsResponse>> {

            override fun onFailure(call: Call<ArrayList<HpDetailsResponse>>, t: Throwable) {
                Log.i("Failure", t.toString())
                if (t is SocketTimeoutException) {

                    toastMessage(this@HpDetailsActivity, R.string.connection_out)


                } else {
                    toastMessage(this@HpDetailsActivity, R.string.internet_off)

                }
            }

            override fun onResponse(call: Call<ArrayList<HpDetailsResponse>>, response: Response<ArrayList<HpDetailsResponse>>) {
                val details: ArrayList<HpDetailsResponse> = response.body()!!
                Log.i("Response HpDetails", details.toString())
                getHpdetails(details)

            }
        })
    }

    private fun getHpdetails(details: ArrayList<HpDetailsResponse>) {
        binding.toolbar.title = details[0].hp.hp_no

        binding.textHpNoLabel.text = details[0].hp.hp_no
        binding.textStatusLabel.text = details[0].hp.status
        binding.textAmountLabel.text = details[0].hp.amount
        binding.textNoOfMonthLabel.text = details[0].hp.no_of_month
        binding.textInterestRateLabel.text = details[0].hp.interest_rate
        binding.textStartDateLabel.text = details[0].hp.start_date
        binding.textEmiLabel.text = details[0].hp.emi.toString()
        binding.textPrincipalLabel.text = details[0].hp.principal
        binding.textPendingDueLabel.text = details[0].hp.pending_due

        binding.textVehicleNameLabel.text = details[0].vehicle.name
        binding.textVehicleColorLabel.text = details[0].vehicle.color
        binding.textVehicleMakeLabel.text = details[0].vehicle.make
        binding.textVehicleNumberLabel.text = details[0].vehicle.number
        details[0].vehicle.vehicle_image.let { imageView(this@HpDetailsActivity, binding.imageVehicle, it) }

        binding.textPartyNameLabel.text = details[0].party.name
        binding.textPartyAddressLabel.text = details[0].party.address
        binding.textPartyLandmarkLabel.text = details[0].party.landmark
        binding.textPartyMobileLabel.text = details[0].party.phone
        binding.textPartyOccupationLabel.text = details[0].party.occupation
        details[0].party.party_image.let { imageView(this@HpDetailsActivity, binding.imageParty, it) }
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
