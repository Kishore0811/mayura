package com.primemover.mayura.pending

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import kotlinx.android.synthetic.main.activity_recyclerview_pendinglist.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException

class PendingListActivity : AppCompatActivity(), View.OnClickListener, TextWatcher {

    lateinit var binding: ActivityRecyclerviewPendinglistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_recyclerview_pendinglist)

        binding.from.addTextChangedListener(this)
        binding.to.addTextChangedListener(this)
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



                if (to < from) {

                    Toast.makeText(this, getString(R.string.from_to_error),
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

    override fun afterTextChanged(s: Editable?) {
        when (s.hashCode()) {

            binding.from.text.hashCode() -> {
                if (binding.from.text.isNullOrEmpty()) {
                    binding.from.error = getString(R.string.from_error)
                    return
                }
                else {
                    binding.from.error = null
                }
            }
            binding.to.text.hashCode() -> {
                if (binding.to.text.isNullOrEmpty()) {
                    binding.to.error = getString(R.string.to_error)
                    return
                }
                else {
                    binding.to.error = null
                }
            }


        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

}