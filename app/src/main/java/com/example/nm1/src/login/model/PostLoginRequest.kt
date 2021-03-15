package com.example.nm1.src.login.model

import com.google.gson.annotations.SerializedName

data class PostLoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)
