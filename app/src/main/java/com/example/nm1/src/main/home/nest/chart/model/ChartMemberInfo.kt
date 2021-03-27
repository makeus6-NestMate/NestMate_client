package com.example.nm1.src.main.home.nest.chart.model

import androidx.annotation.Nullable
import com.google.gson.annotations.SerializedName

data class ChartMemberInfo (
    @SerializedName("userId") val userId: Int,
    @SerializedName("profileImg") val profileImg: String?=null,
    @SerializedName("nickname") val nickname: String
)