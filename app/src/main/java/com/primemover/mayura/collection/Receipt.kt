package com.primemover.mayura.collection

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Receipt(val principal: String,
                   val interest: String,
                   val penalty: String,
                   val account: String,
                   val total: String
) : Parcelable




