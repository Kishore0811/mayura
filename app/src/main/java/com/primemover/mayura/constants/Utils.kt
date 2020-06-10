package com.primemover.mayura.constants

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.primemover.mayura.R

object Utils {

    //const val  BASE_URL = "http://mayurafinance.in/mayura/index.php/api/"    //Development Url

    const val BASE_URL = "http://primeaccounts.in/mayura/index.php/api/"  //Production Url

    fun hideSoftKeyBoard(context: Context, view: View) {
        try {
            val keyboard = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        } catch (e: Exception) {
            // TODO: handle exception
            e.printStackTrace()
        }
    }

    fun imageView(context: Context, imageView: ImageView, bitmap: Any) {
        var requestOptions = RequestOptions()
        requestOptions = requestOptions
                .placeholder(R.drawable.ic_profile)
                .centerCrop()
        Glide.with(context)
                .asBitmap()
                .load(bitmap)
                .apply(requestOptions)
                .into(imageView)
    }

    fun toastMessage(context: Context, message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}