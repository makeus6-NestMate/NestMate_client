package com.example.nm1.src.register.model

import com.google.gson.annotations.SerializedName

data class PostCodeAuthRequest(
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("code") val code: String
)
