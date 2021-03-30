package com.nestmate.nm1.src.main.mypage.model

import com.google.gson.annotations.SerializedName

data class ResultProfile(@SerializedName("user") val user: ProfileData)
