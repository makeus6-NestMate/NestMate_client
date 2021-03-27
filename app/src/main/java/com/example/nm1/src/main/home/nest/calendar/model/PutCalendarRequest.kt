package com.example.nm1.src.main.home.nest.calendar.model

import com.google.gson.annotations.SerializedName

data class PutCalendarRequest (
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("time") val time: String,
    @SerializedName("category") val category: String,
    @SerializedName("categoryIdx") val categoryIdx: Int
)