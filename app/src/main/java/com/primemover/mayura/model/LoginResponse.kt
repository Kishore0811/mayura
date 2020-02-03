package com.primemover.mayura.model

import java.io.Serializable

data class LoginResponse(val message: String,
                         val status: Int,
                         val user_id: Int,
                         val user_name: String,
                         val user_type: String,
                         val token: String): Serializable {
}