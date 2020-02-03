package com.primemover.mayura.api


import com.primemover.mayura.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import com.primemover.mayura.model.PendingHpResponse

interface APIInterface {

    @FormUrlEncoded
    @POST("User/login")

    fun login(
            @Field("username") username: String,
            @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("Hp/pendinghp")

    fun pendinghp(
            @Field("from") from: String,
            @Field("to") to: String
    ): Call<ArrayList<PendingHpResponse>>

    }
