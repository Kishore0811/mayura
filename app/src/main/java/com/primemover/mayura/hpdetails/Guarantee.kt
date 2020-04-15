package com.primemover.mayura.hpdetails

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Guarantee(val name: String,
                     val guarantee_image: String,
                     val address: String,
                     val phone: String,
                     val occupation: String,
                     val landmark: String
) : Parcelable