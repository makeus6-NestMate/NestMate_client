package com.nestmate.nm1.src.login.model

import com.google.gson.annotations.SerializedName

data class PostKakaoLoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("access_token") val access_token:String
)
