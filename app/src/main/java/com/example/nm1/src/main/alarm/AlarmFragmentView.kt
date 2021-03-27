package com.example.nm1.src.main.alarm

import com.example.nm1.src.main.alarm.model.GetAlarmResponse
import com.example.nm1.src.main.home.nest.calendar.model.AddCalendarResponse

interface AlarmFragmentView {
    fun onGetAlarmSuccess(response: GetAlarmResponse)
    fun onGetAlarmFailure(message: String)
}