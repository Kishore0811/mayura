package com.primemover.mayura.pending

import java.io.Serializable

data class PendingHpResponse(val hp_id: String,
                             val hp_no: String,
                             val name: String,
                             val address: String,
                             val emi: Float,
                             val amount: String,
                             val mobile_no: String,
                             val vehicle_no: String,
                             val pending_dues: String): Serializable