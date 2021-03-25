package com.example.nm1.src.main.home.nest.member.model

import com.google.gson.annotations.SerializedName

data class Member(@SerializedName("profileImg") val profileImg: String,
                  @SerializedName("nickname") val nickname: String,
                  @SerializedName("userId") val userId: Int,
                  @SerializedName("isSelf") val isSelf: Boolean
)