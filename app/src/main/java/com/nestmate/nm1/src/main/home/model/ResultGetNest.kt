package com.nestmate.nm1.src.main.home.model

import com.google.gson.annotations.SerializedName

data class ResultGetNest(@SerializedName("roomInfo") val roomInfo: List<NestInfo>,
                         @SerializedName("userName") val userName: String)
