package com.nestmate.nm1.src.main.alarm

import com.nestmate.nm1.src.main.alarm.model.GetAlarmResponse

interface AlarmFragmentView {
    fun onGetAlarmSuccess(response: GetAlarmResponse)
    fun onGetAlarmFailure(message: String)
}