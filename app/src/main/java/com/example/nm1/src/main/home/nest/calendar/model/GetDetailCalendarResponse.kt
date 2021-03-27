package com.example.nm1.src.main.home.nest.calendar.model

import com.example.nm1.config.BaseResponse
import com.example.nm1.src.main.home.nest.todo.model.ResultGetOneDayTodo
import com.google.gson.annotations.SerializedName

data class GetDetailCalendarResponse (@SerializedName("result") val result: ResultGetDetailCalendar): BaseResponse()