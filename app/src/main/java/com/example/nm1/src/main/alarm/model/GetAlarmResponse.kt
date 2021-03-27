package com.example.nm1.src.main.alarm.model

import com.example.nm1.config.BaseResponse
import com.example.nm1.src.main.home.nest.chart.model.ResultGetChart
import com.google.gson.annotations.SerializedName

data class GetAlarmResponse (@SerializedName("result") val result: ResultGetAlarm): BaseResponse()