package com.example.nm1.src.main.home.model

import com.google.gson.annotations.SerializedName

data class PutEditNestRequest(
    @SerializedName("color") val color: String,
    @SerializedName("name") val name: String
)
