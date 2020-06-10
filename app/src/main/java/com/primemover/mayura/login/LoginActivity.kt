package com.primemover.mayura.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.primemover.mayura.R
import com.primemover.mayura.api.APIClient
import com.primemover.mayura.constants.SharedPrefManager
import com.primemover.mayura.constants.Utils.toastMessage
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

                    toastMessage(this, R.string.empty_fields)

                } else {

                    APIClient.instance.login(username, password)
                            .enqueue(object : Callback<LoginResponse> {

                                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

                                    // Timeout exception
                                    if (t is SocketTimeoutException) {

                                        toastMessage(this@LoginActivity, R.string.connection_out)
                                    }

                                    // Internet is turned off
                                    Snackbar.make(v, getString(R.string.internet_off), Snackbar.LENGTH_LONG)
                                            .show()

                                    // To clear the input fields
                                    emptyData()

                                }

                                override fun onResponse(call: Call<LoginResponse>,
                                                        response: Response<LoginResponse>) {

//                                    Log.i("Response", response.code().toString())
                                    when (response.body()?.status) {
                                        204 -> {

                                            toastMessage(this@LoginActivity, R.string.wrong_credentials)
                                        }
                                        200 -> {
                                            val storeUser = response.body()

                                            //Log.i("kishore", storeUser.toString())

                                            // Storing user credentials in Shared Preferences
                                            SharedPrefManager.getInstance(this@LoginActivity)
                                                    .saveUser(storeUser!!)

                                            toastMessage(this@LoginActivity, R.string.logged_in)

                                            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                                                    Intent.FLAG_ACTIVITY_CLEAR_TASK

                                            startActivity(intent)
                                            finish()
                                        }
                                        else -> {
                                            toastMessage(this@LoginActivity, R.string.incorrect)

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