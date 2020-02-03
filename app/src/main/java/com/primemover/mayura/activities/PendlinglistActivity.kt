package com.primemover.mayura.activities

import PendingHpAdapter
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.primemover.mayura.R
import com.primemover.mayura.api.APIClient
import com.primemover.mayura.databinding.ActivityRecyclerviewPendinglistBinding
import com.primemover.mayura.model.PendingHpResponse
import kotlinx.android.synthetic.main.activity_recyclerview_pendinglist.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PendlinglistActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityRecyclerviewPendinglistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_recyclerview_pendinglist)

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
                hideKeyboard()

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
                }

                progressBar.visibility = View.VISIBLE

                APIClient.instance.pendinghp(from, to)
                        .enqueue(object : Callback<ArrayList<PendingHpResponse>> {

                            override fun onFailure(call: Call<ArrayList<PendingHpResponse>>,
                                                   t: Throwable) {
                                progressBar.visibility = View.GONE
                                onBackPressed()
                                Snackbar.make(v, "You are Offline", Snackbar.LENGTH_LONG)
                                        .show()
                            }

                            override fun onResponse(call: Call<ArrayList<PendingHpResponse>>,
                                                    response: Response<ArrayList<PendingHpResponse>>) {

                                if (response.code() in 200..210) {
                                    progressBar.visibility = View.GONE
                                    recyclerViewPending.visibility = View.VISIBLE
                                    //Log.i("Response Pending:", response.body().toString())
                                    val pendinglist = response.body()

                                    pendinglist?.let {
                                        showpendinglist(it)
                                    }
                                } else {
                                    Toast.makeText(applicationContext, "Server Down",
                                            Toast.LENGTH_SHORT).show()
                                }

                            }

                        })
            }
        }
    }

    private fun showpendinglist(pendingList: ArrayList<PendingHpResponse>) {
        val pendingAdapter = PendingHpAdapter(this, pendingList)
        recyclerViewPending.layoutManager = LinearLayoutManager(this)
        recyclerViewPending.adapter = pendingAdapter
        pendingAdapter.notifyDataSetChanged()
    }

    private fun AppCompatActivity.hideKeyboard() {

        val view = this.currentFocus

        if (view != null) {

            val keyboard = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.hideSoftInputFromWindow(view.windowToken, 0)
        }
        // else {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        // }
    }

    override fun onBackPressed() {
        super.onBackPressed()

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