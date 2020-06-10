package com.primemover.mayura.api

import com.primemover.mayura.constants.Utils.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIClient {

    private val okHttpClient= OkHttpClient.Builder()
            .addInterceptor{chain ->
                val original = chain.request()

                val requestBuilder = original.newBuilder()
                        .method(original.method(), original.body())

                val request = requestBuilder.build()
                chain.proceed(request)
            }.build()

    val instance: APIInterface by lazy{
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

        retrofit.create(APIInterface::class.java)
    }
}