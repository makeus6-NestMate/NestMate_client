package com.example.nm1.src.main.alarm.model

import com.example.nm1.src.main.home.nest.calendar.model.CalendarInfo
import com.google.gson.annotations.SerializedName

data class ResultGetAlarm (@SerializedName("alarm") val alarmInfo: List<AlarmInfo>)