package com.example.nm1.src.main.home.nest.calendar.model

import com.example.nm1.src.main.home.model.NestInfo
import com.google.gson.annotations.SerializedName

data class ResultGetCalendar (@SerializedName("calendar") val calendarInfo: List<CalendarInfo>)