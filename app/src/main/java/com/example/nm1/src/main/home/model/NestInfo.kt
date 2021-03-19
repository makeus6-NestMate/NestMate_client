package com.example.nm1.src.main.home.model

import com.google.gson.annotations.SerializedName

data class NestInfo(@SerializedName("roomId") val roomId: Int,
                    @SerializedName("roomName") val roomName: String,
                    @SerializedName("roomColor") val roomColor: String,
                    @SerializedName("members") val members: List<NestMember>)