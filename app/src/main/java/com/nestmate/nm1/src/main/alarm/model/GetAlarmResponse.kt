package com.nestmate.nm1.src.main.alarm.model

import com.nestmate.nm1.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class GetAlarmResponse (@SerializedName("result") val result: ResultGetAlarm): BaseResponse()