package com.primemover.mayura.collection

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CollectionResponse(val hp: Hp,
                              val receipt: Receipt,
                              val precloser: Precloser) : Parcelable