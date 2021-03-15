package com.example.nm1.src.register.model

import com.google.gson.annotations.SerializedName

data class PostPhoneAuthRequest(
    @SerializedName("phoneNumber") val phoneNumber: String
)
