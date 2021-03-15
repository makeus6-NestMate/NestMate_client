package com.example.nm1.src.register.model

import com.google.gson.annotations.SerializedName
import java.io.File

data class PostUserSignUpRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("name") val name: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("img") val img: File
)
