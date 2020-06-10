package com.primemover.mayura.pending

import java.io.Serializable

data class PendingHp(val hp_id: String,
                     val hp_no: String,
                     val name: String,
                     val image: String,
                     val address: String,
                     val emi: Float,
                     val amount: String,
                     val mobile_no: String,
                     val vehicle_no: String,
                     val pending_dues: String) : Serializable