package com.primemover.mayura.pending

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PendingHpResponse(val status: Int,
                             val hppending_count: Int,
                             val hppending: ArrayList<PendingHp>) : Parcelable