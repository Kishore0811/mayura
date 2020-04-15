package com.primemover.mayura.hpdetails

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Party(val name: String,
                 val party_image: String,
                 val address: String,
                 val phone: String,
                 val occupation: String,
                 val landmark: String
) : Parcelable