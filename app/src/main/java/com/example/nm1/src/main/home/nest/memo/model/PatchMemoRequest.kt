package com.example.nm1.src.main.home.nest.memo.model

import com.google.gson.annotations.SerializedName

data class PatchMemoRequest(@SerializedName("x") val x: Float, @SerializedName("y") val y: Float)
