package com.primemover.mayura.hpdetails

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Hp(val hp_no: String,
              val status: String,
              val amount: String,
              val no_of_month: String,
              val interest_rate: String,
              val start_date: String,
              val emi: Float,
              val principal: String,
              val pending_due: String,
              val cheazing_status: String
) : Parcelable