package com.primemover.mayura.collection

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Precloser(val penalty: String,
                     val discount: String,
                     val paid_amount: String
) : Parcelable