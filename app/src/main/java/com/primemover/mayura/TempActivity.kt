package com.primemover.mayura
//
//import android.content.Intent
//import android.graphics.Color
//import android.os.Bundle
//import android.text.Editable
//import android.text.TextWatcher
//import android.util.Log
//import android.view.View
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.databinding.DataBindingUtil
//import com.github.razir.progressbutton.attachTextChangeAnimator
//import com.github.razir.progressbutton.bindProgressButton
//import com.github.razir.progressbutton.showProgress
//import com.google.android.material.snackbar.Snackbar
//import com.primemover.mayura.api.APIClient
//import com.primemover.mayura.constants.SharedPrefManager
//import com.primemover.mayura.databinding.ActivityTempBinding
//import com.primemover.mayura.home.HomeActivity
//import com.primemover.mayura.login.LoginResponse
//import kotlinx.android.synthetic.main.activity_temp.*
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import java.net.SocketTimeoutException
//
//
//class TempActivity : AppCompatActivity(), View.OnClickListener, TextWatcher {
//
//    lateinit var binding: ActivityTempBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_temp)
//        bindProgressButton(submit)
//        binding.username.addTextChangedListener(this)
//        binding.password.addTextChangedListener(this)
//
//        submit.attachTextChangeAnimator()
//        binding.submit.setOnClickListener(this)
//    }
//
//    override fun onClick(v: View?) {
//
//        when (v!!.id) {
//
//            R.id.submit -> {
//                val username = binding.username.text.toString().trim()
//                val password = binding.password.text.toString().trim()
//
//                if (username.isEmpty() && password.isEmpty()) {
//                    Toast.makeText(this, getString(R.string.empty_fields), Toast.LENGTH_SHORT).show()
//
//                } else {
//
//                    //binding.loginProgressBar.visibility = View.VISIBLE
//                    submit.showProgress {
//                        buttonTextRes = R.string.logging_in
//                        progressColor = Color.WHITE
//                    }
////                    submit.isEnabled = false
////                    Handler().postDelayed({
////                        submit.isEnabled = true
////                        submit.hideProgress(R.string.logged_in)
////                    }, 3000)
//
//                    APIClient.instance.login(username, password)
//                            .enqueue(object : Callback<LoginResponse> {
//
//                                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                                    //binding.loginProgressBar.visibility = View.GONE
//                                    // Timeout exception
//                                    if (t is SocketTimeoutException) {
//                                        Toast.makeText(this@TempActivity, getString(R.string.connection_out), Toast.LENGTH_SHORT).show()
//                                    }
//
//                                    // Internet is turned off
//                                    Snackbar.make(v, getString(R.string.internet_off), Snackbar.LENGTH_LONG)
//                                            .show()
//
//                                    // To clear the input fields
//                                    emptyData()
//
//                                }
//
//                                override fun onResponse(call: Call<LoginResponse>,
//                                                        response: Response<LoginResponse>) {
//
//                                    Log.i("Response", response.code().toString())
//                                    //binding.loginProgressBar.visibility = View.GONE
//
//                                    when (response.body()?.status) {
//                                        204 -> {
//                                            Toast.makeText(this@TempActivity,
//                                                    getString(R.string.wrong_credentials), Toast.LENGTH_SHORT).show()
//                                        }
//                                        200 -> {
//                                            val storeUser = response.body()
//
//                                            //Log.i("kishore", storeUser.toString())
//
//                                            // Storing user credentials in Shared Preferences
//                                            SharedPrefManager.getInstance(this@TempActivity)
//                                                    .saveUser(storeUser!!)
//
//                                            Toast.makeText(this@TempActivity, R.string.logged_in,
//                                                    Toast.LENGTH_LONG).show()
//
//                                            val intent = Intent(this@TempActivity, HomeActivity::class.java)
//                                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
//                                                    Intent.FLAG_ACTIVITY_CLEAR_TASK
//
//                                            startActivity(intent)
//                                            finish()
//                                        }
//                                        else -> {
//                                            Toast.makeText(this@TempActivity, response.body()?.message,
//                                                    Toast.LENGTH_LONG).show()
//                                            emptyData()
//
//                                        }
//                                    }
//
//                                }
//
//                            })
//                }
//
//            }
//
//
//        }
//    }
//
//
//    //To clear the input fields
//    private fun emptyData() {
//        binding.username.setText("")
//        binding.password.setText("")
//
//    }
//
//
//    override fun onStart() {
//        super.onStart()
//
//        //Whether the user is logged in or not
//        if (SharedPrefManager.getInstance(mCtx = this).isLoggedIn) {
//
//            val intent = Intent(this, HomeActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(intent)
//            finish()
//        }
//    }
//
//    override fun afterTextChanged(s: Editable?) {
//
//        when (s.hashCode()) {
//
//            binding.username.text.hashCode() -> {
//                if (binding.username.text.isNullOrEmpty()) {
//                    binding.username.error = getString(R.string.username_error)
//                    return
//                } else {
//                    binding.username.error = null
//                }
//            }
//            binding.password.text.hashCode() -> {
//
//                if (binding.password.text.isNullOrEmpty()) {
//                    binding.password.error = getString(R.string.password_error)
//                    return
//                } else {
//                    binding.password.error = null
//                }
//            }
//        }
//    }
//
//    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//    }
//
//    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//    }
//}