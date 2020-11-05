package com.primemover.mayura.search

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.primemover.mayura.R
import com.primemover.mayura.api.APIClient
import com.primemover.mayura.databinding.ActivitySearchBinding
import kotlinx.android.synthetic.main.activity_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {

    lateinit var binding: ActivitySearchBinding
    private lateinit var call: Call<ArrayList<SearchResponse>>
    var allSearchList: ArrayList<SearchResponse> = ArrayList()
    var searchKey = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.searchBar.clearFocus()
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            val mHandler = Handler(Looper.getMainLooper())

            override fun onQueryTextSubmit(query: String): Boolean {
                searchKey = query
                if (!isFinishing)
                    getSearchValue(searchKey, true)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                mHandler.removeCallbacksAndMessages(null)
                mHandler.postDelayed({
                    searchKey = newText
                    if (!isFinishing)
                        getSearchValue(searchKey, true)


                }, 2000)
                return true
            }

        })
    }


    private fun getSearchValue(searchKey: String, isSearching: Boolean) {

        if (!isFinishing)
            binding.progressBar.visibility = View.VISIBLE
        call = APIClient.instance.search(searchKey)
        call.enqueue(object : Callback<ArrayList<SearchResponse>> {
            override fun onResponse(call: Call<ArrayList<SearchResponse>>, response: Response<ArrayList<SearchResponse>>) {
                if (!isFinishing)
                    binding.progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    val searchTemp = response.body()!!

                    if (isSearching) {
                        allSearchList.clear()
                        when {
                            searchTemp.size > 0 -> {
                                binding.searchRecyclerView.visibility = View.VISIBLE
                                binding.noItemTxt.visibility = View.GONE
                                showSearchList(searchTemp)
                                binding.searchBar.clearFocus()

                            }
                            else -> {
                                binding.searchRecyclerView.visibility = View.GONE
                                binding.noItemTxt.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }


            override fun onFailure(call: Call<ArrayList<SearchResponse>>, t: Throwable) {
                Log.i("ERRRRROR", t.message!!)

                if (!isFinishing)
                //progressDialog.dismiss()
                    binding.progressBar.visibility = View.GONE
                if (call.isCanceled) {
                    Log.i("Call Status", "isCanceled")
                    onBackPressed()
                } else {
                    Toast.makeText(
                            this@SearchActivity, getString(R.string.internet_off),
                            Toast.LENGTH_SHORT
                    ).show()
                    call.cancel()
                }

            }

        })

    }

    private fun showSearchList(searchList: ArrayList<SearchResponse>) {
        val searchAdapter = SearchAdapter(this, searchList)
        searchRecyclerView.layoutManager = LinearLayoutManager(this)
        searchRecyclerView.adapter = searchAdapter
        searchAdapter.notifyDataSetChanged()
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
