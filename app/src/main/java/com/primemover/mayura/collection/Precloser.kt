package com.primemover.mayura.collection

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Precloser(val penalty: Int,
                     val discount: Int,
                     val paid_amount: Int
): Parcelable