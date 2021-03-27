package com.example.nm1.src.main.home.nest.calendar.model

import com.google.gson.annotations.SerializedName

data class CalendarInfo (
    @SerializedName("time") val time: String,
    @SerializedName("categories") val categories: List<CalendarCategoryInfo>
)