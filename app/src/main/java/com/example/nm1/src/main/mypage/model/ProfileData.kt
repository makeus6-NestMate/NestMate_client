package com.example.nm1.src.main.mypage.model

import com.google.gson.annotations.SerializedName

data class ProfileData(@SerializedName("profileImg") val profileImg: String,

                       @SerializedName("nickname") val nickname: String,
                       @SerializedName("prizeCount") val prizeCount: Int
                       )
