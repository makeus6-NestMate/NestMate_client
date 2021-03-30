package com.nestmate.nm1.src.main.home.nest.calendar.model

import com.nestmate.nm1.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class GetCalendarResponse (@SerializedName("result") val result: ResultGetCalendar): BaseResponse()