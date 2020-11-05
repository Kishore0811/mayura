package com.primemover.mayura.search

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchResponse(val status: Int,
                          val hp_id: String,
                          val hp_no: String,
                          val name: String,
                          val image: String,
                          val address: String,
                          val emi: Float,
                          val amount: String,
                          val mobile_no: String,
                          val vehicle_no: String,
                          val pending_dues: String,
                          val cheazing_status: String
) : Parcelable