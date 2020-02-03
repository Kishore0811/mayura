package com.primemover.mayura.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.primemover.mayura.R
import com.primemover.mayura.api.APIClient
import com.primemover.mayura.constants.SharedPrefManager
import com.primemover.mayura.databinding.ActivityLoginBinding
import com.primemover.mayura.model.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.username.text
        binding.password.text
        binding.submit.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        when (v!!.id) {
            R.id.submit -> {
                val username = binding.username.text.toString().trim()
                val password = binding.password.text.toString().trim()

                if (username.isEmpty()) {
                    binding.usernameTextInputLayout.error = "Username Required"
                    binding.username.requestFocus()
                    return
                }
                if (password.isEmpty()) {
                    binding.passwordTextInputLayout.error = "Password Required"
                    binding.password.requestFocus()
                    return
                }


                APIClient.instance.login(username, password)
                        .enqueue(object : Callback<LoginResponse> {

                            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                                Snackbar.make(v, "You are Offline", Snackbar.LENGTH_LONG)
                                        .show()
                                emptyData()

                            }

                            override fun onResponse(call: Call<LoginResponse>,
                                                    response: Response<LoginResponse>) {

                                //Log.i("Response", response.body().toString())
                                if (response.body()?.status == 200) {
                                    val temp = response.body()

                                    //Log.i("kishore", temp.toString())
                                    SharedPrefManager.getInstance(applicationContext)
                                            .saveUser(temp!!)
                                    Toast.makeText(applicationContext, response.body()?.message,
                                            Toast.LENGTH_LONG).show()

                                    val intent = Intent(applicationContext, HomeActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                                            Intent.FLAG_ACTIVITY_CLEAR_TASK

                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(applicationContext, response.body()?.message,
                                            Toast.LENGTH_LONG).show()
                                    emptyData()

                                }

                            }

                        })

            }


        }
    }

    private fun emptyData() {
        binding.username.setText("")
        binding.password.setText("")

    }

    override fun onStart() {
        super.onStart()

        if (SharedPrefManager.getInstance(mCtx = this).isLoggedIn) {

            val intent = Intent(applicationContext, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}