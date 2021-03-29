package com.example.nm1.src.main.home.nest.calendar.model

import com.google.gson.annotations.SerializedName

data class ResultGetDetailCalendar (@SerializedName("calendar") val calendarDetailInfo: List<CalendarDetailInfo>, @SerializedName("calendarCnt") val calendarCnt:Int)