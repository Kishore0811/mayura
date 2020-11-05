package com.primemover.mayura.hpdetails

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DueDetails(val due_date: String,
                      val receipt: String,
                      val amount: Int
) : Parcelable