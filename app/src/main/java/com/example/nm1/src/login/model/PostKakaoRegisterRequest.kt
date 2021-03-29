package com.example.nm1.src.login.model

import com.google.gson.annotations.SerializedName

data class PostKakaoRegisterRequest(
    @SerializedName("nickname") val nickname: String,
    @SerializedName("profileImg") val profileImg: String?=null,
    @SerializedName("email") val email: String
)
