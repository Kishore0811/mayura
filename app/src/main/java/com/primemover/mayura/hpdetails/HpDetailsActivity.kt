package com.primemover.mayura.hpdetails

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.primemover.mayura.R
import com.primemover.mayura.api.APIClient
import com.primemover.mayura.constants.Utils.imageView
import com.primemover.mayura.constants.Utils.imageViewVehicle
import com.primemover.mayura.constants.Utils.toastMessage
import com.primemover.mayura.databinding.ActivityHpdetailsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException

class HpDetailsActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityHpdetailsBinding
    lateinit var hpdetails: HpDetailsResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_hpdetails)
        binding.phone.setOnClickListener(this)
        binding.guaranteePhone.setOnClickListener(this)
        //applyTheme("dark")
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //Getting value from pending hp adapter
        val hpId = intent.getStringExtra("hpId")

        binding.progressHpdetails.visibility = View.VISIBLE

        APIClient.instance.hpdetails(hpId!!).enqueue(object : Callback<HpDetailsResponse> {

            override fun onFailure(call: Call<HpDetailsResponse>, t: Throwable) {
                Log.i("Failure", t.toString())

                binding.progressHpdetails.visibility = View.GONE
                //Timeout Exception
                if (t is SocketTimeoutException) {

                    toastMessage(this@HpDetailsActivity, R.string.connection_out)


                } else {
                    toastMessage(this@HpDetailsActivity, R.string.internet_off)
                }
            }

            override fun onResponse(call: Call<HpDetailsResponse>, response: Response<HpDetailsResponse>) {
                binding.progressHpdetails.visibility = View.GONE

                if (response.isSuccessful) {
                    binding.scrollView.visibility = View.VISIBLE
                    val details: HpDetailsResponse = response.body()!!
                    getHpDetails(details)
                    //showDueDetailsList(details)
                    hpdetails = details

                } else {
                    toastMessage(this@HpDetailsActivity, R.string.error)
                }

            }
        })

    }

    override fun onClick(v: View?) {

        val partyPhone: String = hpdetails.party.phone
        val guarantorPhone: String = hpdetails.guarantee.phone
        when (v!!.id) {
            R.id.phone -> {
                dial(partyPhone)
            }
            R.id.guarantee_phone -> {
                dial(guarantorPhone)
            }
        }
    }

    private fun getHpDetails(details: HpDetailsResponse) {
        binding.toolbar.title = details.hp.hp_no

        //Hp Details
        binding.textStatusLabel.text = details.hp.status
        binding.textAmountLabel.text = details.hp.amount
        binding.textNoOfMonthLabel.text = details.hp.no_of_month
        binding.textInterestRateLabel.text = details.hp.interest_rate
        binding.textStartDateLabel.text = details.hp.start_date
        binding.textEmiLabel.text = details.hp.emi.toString()
        binding.textPrincipalLabel.text = details.hp.principal
        binding.textPendingDueLabel.text = details.hp.pending_due
        binding.textCeasedLabel.text = details.hp.cheazing_status

        //Vehicle Details
        binding.textVehicleNameLabel.text = details.vehicle.name
        binding.textVehicleColorLabel.text = details.vehicle.color
        binding.textVehicleMakeLabel.text = details.vehicle.make
        binding.textVehicleNumberLabel.text = details.vehicle.number
        imageViewVehicle(this, binding.imageVehicle, details.vehicle.vehicle_image)

        //Party Details
        binding.textPartyNameLabel.text = details.party.name
        binding.textPartyAddressLabel.text = details.party.address
        binding.textPartyLandmarkLabel.text = details.party.landmark
        binding.textPartyMobileLabel.text = details.party.phone
        binding.textPartyOccupationLabel.text = details.party.occupation
        imageView(this, binding.imageParty, details.party.party_image)

        //Guarantor Details
        binding.textGuaranteeNameLabel.text = details.guarantee.name
        binding.textGuaranteePhoneLabel.text = details.guarantee.phone
        binding.textGuaranteeOccupationLabel.text = details.guarantee.occupation
        binding.textGuaranteeAddressLabel.text = details.guarantee.address
        binding.textGuaranteeLandmarkLabel.text = details.guarantee.landmark
        imageView(this, binding.imageGuarantee, details.guarantee.guarantee_image)

        val dueDetailsAdapter = DueDetailsAdapter(details.hp_details)
        binding.recyclerViewDueDetails.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewDueDetails.adapter = dueDetailsAdapter
        dueDetailsAdapter.notifyDataSetChanged()
    }

//    private fun showDueDetailsList(dueDetails: HpDetailsResponse) {
//        val dueDetailsAdapter = DueDetailsAdapter( dueDetails)
//        binding.recyclerViewDueDetails.layoutManager = LinearLayoutManager(this)
//        binding.recyclerViewDueDetails.adapter = dueDetailsAdapter
//        dueDetailsAdapter.notifyDataSetChanged()
//    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    private fun dial(number: String) {
        when {
            number == "" -> {
                toastMessage(this, R.string.error_number)
                return
            }

            this.checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager
                    .PERMISSION_GRANTED -> {
                val i = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
                this.startActivity(i)
            }

            else -> ActivityCompat.requestPermissions(this, arrayOf(Manifest
                    .permission.CALL_PHONE), 1)
        }
    }
}
