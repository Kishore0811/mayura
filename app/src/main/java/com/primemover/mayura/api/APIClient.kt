package com.primemover.mayura.api

import com.primemover.mayura.constants.Utils.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIClient {
    val instance: APIInterface
        get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val instance = OkHttpClient.Builder().addInterceptor(interceptor).build()


            return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(instance)
                    .build()
                    .create(APIInterface::class.java)
        }


}