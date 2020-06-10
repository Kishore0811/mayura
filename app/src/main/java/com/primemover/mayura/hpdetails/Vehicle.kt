package com.primemover.mayura.hpdetails

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Vehicle(val name: String,
                   val vehicle_image: String,
                   val color: String,
                   val make: String,
                   val number: String
) : Parcelable