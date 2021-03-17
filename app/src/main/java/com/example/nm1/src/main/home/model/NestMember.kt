package com.example.nm1.src.main.home.model

import com.google.gson.annotations.SerializedName

data class NestMember(@SerializedName("profileImg") val profileImg: String,
                      @SerializedName("nickname") val nickname: String)