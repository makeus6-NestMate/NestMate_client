package com.example.nm1.src.main.alarm.model

import com.google.gson.annotations.SerializedName

data class AlarmInfo (@SerializedName("message") val message: String,
                      @SerializedName("profileImg") val profileImg: String?=null,
                      @SerializedName("createdAt") val createdAt: String)