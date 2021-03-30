package com.nestmate.nm1.src.main.alarm.model

import com.google.gson.annotations.SerializedName

data class ResultGetAlarm (@SerializedName("alarm") val alarmInfo: List<AlarmInfo>)