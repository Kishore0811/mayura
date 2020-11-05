package com.primemover.mayura.hpdetails

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HpDetailsResponse(val hp: Hp,
                             val vehicle: Vehicle,
                             val party: Party,
                             val guarantee: Guarantee,
                             val hp_details: ArrayList<DueDetails>
) : Parcelable