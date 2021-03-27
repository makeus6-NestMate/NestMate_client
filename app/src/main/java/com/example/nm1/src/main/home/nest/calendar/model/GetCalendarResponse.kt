package com.example.nm1.src.main.home.nest.calendar.model

import com.example.nm1.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class GetCalendarResponse (@SerializedName("result") val result: ResultGetCalendar): BaseResponse()