package com.nestmate.nm1.src.main.home.nest.calendar.model

import com.google.gson.annotations.SerializedName

data class PostAddCalendarRequest (
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("time") val time: String,
    @SerializedName("category") val category: String,
    @SerializedName("categoryIdx") val categoryIdx: Int
)