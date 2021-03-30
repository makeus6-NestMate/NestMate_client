package com.nestmate.nm1.src.main.home.nest.chart.model

import com.google.gson.annotations.SerializedName

data class ChartBestInfo (
    @SerializedName("userId") val userId: Int,
    @SerializedName("profileImg") val profileImg: String?=null,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("prizeCount") val prizeCount : Int)