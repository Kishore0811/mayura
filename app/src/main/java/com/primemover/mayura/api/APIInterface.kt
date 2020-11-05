package com.primemover.mayura.api

import com.primemover.mayura.collection.CollectionResponse
import com.primemover.mayura.hpdetails.HpDetailsResponse
import com.primemover.mayura.login.LoginResponse
import com.primemover.mayura.pending.PendingHpResponse
import com.primemover.mayura.search.SearchResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

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

    @POST("Collection/getcollection")
    fun getcollection(): Call<CollectionResponse>

    @FormUrlEncoded
    @POST("Hp/hpdetails")
    fun hpdetails(
            @Field("hp_id") hpId: String
    ): Call<HpDetailsResponse>

    @FormUrlEncoded
    @POST("Pending/getpendinghp")
    fun search(
            @Field("search_param") searchParam: String
    ): Call<ArrayList<SearchResponse>>

}
