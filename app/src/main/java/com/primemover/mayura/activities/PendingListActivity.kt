package com.primemover.mayura.activities

import PendingHpAdapter
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.primemover.mayura.R
import com.primemover.mayura.api.APIClient
import com.primemover.mayura.constants.Utils.hideSoftKeyBoard
import com.primemover.mayura.databinding.ActivityRecyclerviewPendinglistBinding
import com.primemover.mayura.model.PendingHpResponse
import kotlinx.android.synthetic.main.activity_recyclerview_pendinglist.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException

class PendingListActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityRecyclerviewPendinglistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_recyclerview_pendinglist)

        binding.from.text
        binding.to.text
        binding.submit.setOnClickListener(this)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.progressBar

    }

    override fun onClick(v: View?) {

        when (v!!.id) {
            R.id.submit -> {
                val from = binding.from.text.toString().trim()
                val to = binding.to.text.toString().trim()
                binding.to.clearFocus()

                //To hide the keyboard
                hideSoftKeyBoard(this, v)

                if (from.isEmpty()) {
                    binding.fromTextInputLayout.error = "From Required"
                    binding.from.requestFocus()
                    return
                }
                if (to.isEmpty()) {
                    binding.toTextInputLayout.error = "To Required"
                    binding.to.requestFocus()
                    return
                }

                if (to < from) {

                    Toast.makeText(this, "From should be less than or equal to To",
                            Toast.LENGTH_LONG).show()
                } else {

                    progressBar.visibility = View.VISIBLE

                    APIClient.instance.pendinghp(from, to)
                            .enqueue(object : Callback<ArrayList<PendingHpResponse>> {

                                override fun onFailure(call: Call<ArrayList<PendingHpResponse>>,
                                                       t: Throwable) {
                                    progressBar.visibility = View.GONE

                                    //Timeout Exception
                                    if (t is SocketTimeoutException) {

                                        Toast.makeText(this@PendingListActivity,
                                                getString(R.string.connection_out), Toast.LENGTH_SHORT)
                                                .show()


                                    } else {
                                        Snackbar.make(v, getString(R.string.internet_off), Snackbar.LENGTH_LONG)
                                                .show()
                                    }
                                }

                                override fun onResponse(call: Call<ArrayList<PendingHpResponse>>,
                                                        response: Response<ArrayList<PendingHpResponse>>) {

                                    progressBar.visibility = View.GONE
                                    recyclerViewPending.visibility = View.VISIBLE
                                    //Log.i("Response Pending:", response.body().toString())

                                    //Storing the response
                                    val pendinglist = response.body()

                                    // Showing it in recyclerView
                                    pendinglist?.let {
                                        showPendingList(it)
                                    }


                                }

                            })
                }
            }
        }
    }

    private fun showPendingList(pendingList: ArrayList<PendingHpResponse>) {
        val pendingAdapter = PendingHpAdapter(this, pendingList)
        recyclerViewPending.layoutManager = LinearLayoutManager(this)
        recyclerViewPending.adapter = pendingAdapter
        pendingAdapter.notifyDataSetChanged()
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