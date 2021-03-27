package com.example.nm1.src.main.home.nest.calendar.model

import com.google.gson.annotations.SerializedName

class CalendarCategoryInfo (
    @SerializedName("category") val category: String,
    @SerializedName("categoryIdx") val categoryIdx: Int
)