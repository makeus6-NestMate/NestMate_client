package com.nestmate.nm1.src.main.home.nest.calendar.model

import com.google.gson.annotations.SerializedName

data class ResultGetCalendar (@SerializedName("calendar") val calendarInfo: List<CalendarInfo>)