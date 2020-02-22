package com.primemover.mayura.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.primemover.mayura.R
import com.primemover.mayura.api.APIClient
import com.primemover.mayura.constants.SharedPrefManager
import com.primemover.mayura.databinding.ActivityLoginBinding
import com.primemover.mayura.home.HomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException


class LoginActivity : AppCompatActivity(), View.OnClickListener, TextWatcher {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.username.addTextChangedListener(this)
        binding.password.addTextChangedListener(this)
        binding.submit.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        when (v!!.id) {

            R.id.submit -> {
                val username = binding.username.text.toString().trim()
                val password = binding.password.text.toString().trim()

                if (username.isEmpty() && password.isEmpty()) {
                    Toast.makeText(this, getString(R.string.empty_fields), Toast.LENGTH_SHORT).show()

                } else {

                    APIClient.instance.login(username, password)
                            .enqueue(object : Callback<LoginResponse> {

                                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

                                    // Timeout exception
                                    if (t is SocketTimeoutException) {
                                        Toast.makeText(this@LoginActivity, getString(R.string.connection_out), Toast.LENGTH_SHORT).show()
                                    }

                                    // Internet is turned off
                                    Snackbar.make(v, getString(R.string.internet_off), Snackbar.LENGTH_LONG)
                                            .show()

                                    // To clear the input fields
                                    emptyData()

                                }

                                override fun onResponse(call: Call<LoginResponse>,
                                                        response: Response<LoginResponse>) {

                                    Log.i("Response", response.code().toString())
                                    when (response.body()?.status) {
                                        204 -> {
                                            Toast.makeText(this@LoginActivity,
                                                    getString(R.string.wrong_credentials), Toast.LENGTH_SHORT).show()
                                        }
                                        200 -> {
                                            val storeUser = response.body()

                                            //Log.i("kishore", storeUser.toString())

                                            // Storing user credentials in Shared Preferences
                                            SharedPrefManager.getInstance(this@LoginActivity)
                                                    .saveUser(storeUser!!)

                                            Toast.makeText(this@LoginActivity, response.body()?.message,
                                                    Toast.LENGTH_LONG).show()

                                            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                                                    Intent.FLAG_ACTIVITY_CLEAR_TASK

                                            startActivity(intent)
                                            finish()
                                        }
                                        else -> {
                                            Toast.makeText(this@LoginActivity, response.body()?.message,
                                                    Toast.LENGTH_LONG).show()
                                            emptyData()

                                        }
                                    }

                                }

                            })
                }

            }


        }
    }


    //To clear the input fields
    private fun emptyData() {
        binding.username.setText("")
        binding.password.setText("")

    }


    override fun onStart() {
        super.onStart()

        //Whether the user is logged in or not
        if (SharedPrefManager.getInstance(mCtx = this).isLoggedIn) {

            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    override fun afterTextChanged(s: Editable?) {

        when (s.hashCode()) {

            binding.username.text.hashCode() -> {
                if (binding.username.text.isNullOrEmpty()) {
                    binding.username.error = getString(R.string.username_error)
                    return
                } else {
                    binding.username.error = null
                }
            }
            binding.password.text.hashCode() -> {

                if (binding.password.text.isNullOrEmpty()) {
                    binding.password.error = getString(R.string.password_error)
                    return
                } else {
                    binding.password.error = null
                }
            }
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}