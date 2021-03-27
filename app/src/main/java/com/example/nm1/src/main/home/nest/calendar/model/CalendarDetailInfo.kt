package com.example.nm1.src.main.home.nest.calendar.model

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.google.gson.annotations.SerializedName

data class CalendarDetailInfo (
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String?=null,
    @SerializedName("time") val time: String,
    @SerializedName("category") val category: String,
    @SerializedName("categoryIdx") val categoryIdx: Int,
    @SerializedName("calendarId") val calendarId: Int
)