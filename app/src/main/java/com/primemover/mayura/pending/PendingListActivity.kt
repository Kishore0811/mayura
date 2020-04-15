package com.primemover.mayura.pending

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.primemover.mayura.R
import com.primemover.mayura.api.APIClient
import com.primemover.mayura.constants.Utils.hideSoftKeyBoard
import com.primemover.mayura.constants.Utils.toastMessage
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
        binding.to.setOnClickListener(this)
        binding.submit.setOnClickListener(this)
        //applyTheme("dark")
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onClick(v: View?) {

        when (v!!.id) {
            R.id.submit, R.id.to -> {
                val from = binding.from.text.toString().trim()
                val to = binding.to.text.toString().trim()
                binding.to.clearFocus()

                //To hide the keyboard
                hideSoftKeyBoard(this, v)

                if (to < from) {

                    toastMessage(this, R.string.from_to_error)
                } else {

                    pending_list_progressBar.visibility = View.VISIBLE

                    APIClient.instance.pendinghp(from, to)
                            .enqueue(object : Callback<ArrayList<PendingHpResponse>> {

                                override fun onFailure(call: Call<ArrayList<PendingHpResponse>>,
                                                       t: Throwable) {
                                    pending_list_progressBar.visibility = View.GONE

                                    Log.i("Pending failure", t.toString())

                                    //Timeout Exception
                                    when (t) {
                                        is SocketTimeoutException -> {
                                            toastMessage(this@PendingListActivity, R.string.connection_out)

                                        }
                                        is com.google.gson.stream.MalformedJsonException -> {
                                            //t.printStackTrace()
                                            Snackbar.make(v, getString(R.string.data_not_found),
                                                    Snackbar.LENGTH_SHORT).show()
                                        }
                                        else -> {
                                            Snackbar.make(v, getString(R.string.internet_off), Snackbar.LENGTH_LONG)
                                                    .show()
                                        }
                                    }
                                }

                                override fun onResponse(call: Call<ArrayList<PendingHpResponse>>,
                                                        response: Response<ArrayList<PendingHpResponse>>) {

                                    pending_list_progressBar.visibility = View.GONE
                                    recyclerViewPending.visibility = View.VISIBLE
                                    Log.i("Response Pending:", response.body().toString())

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
                } else {
                    binding.from.error = null
                }
            }
            binding.to.text.hashCode() -> {
                if (binding.to.text.isNullOrEmpty()) {
                    binding.to.error = getString(R.string.to_error)
                    return
                } else {
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