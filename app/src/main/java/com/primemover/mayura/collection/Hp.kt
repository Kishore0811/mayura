package com.primemover.mayura.collection

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Hp(val new: String,
              val old: String,
              val finance: String,
              val total: Int,
              val total_amount: String
) : Parcelable