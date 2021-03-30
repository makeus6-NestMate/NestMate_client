package com.nestmate.nm1.src.main.home.model

import com.google.gson.annotations.SerializedName

data class PostAddNestRequest(
    @SerializedName("color") val color: String,
    @SerializedName("name") val name: String
)
