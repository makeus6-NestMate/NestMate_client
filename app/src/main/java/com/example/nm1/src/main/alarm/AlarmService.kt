package com.example.nm1.src.main.alarm

import com.example.nm1.config.ApplicationClass
import com.example.nm1.src.main.alarm.model.GetAlarmResponse
import com.example.nm1.src.main.home.nest.calendar.CalendarActivityView
import com.example.nm1.src.main.home.nest.calendar.CalendarInterface
import com.example.nm1.src.main.home.nest.calendar.model.GetCalendarResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlarmService (val view: AlarmFragmentView) {
    fun tryGetAlarm(roomId:Int, page:Int){
        val alarmInterface = ApplicationClass.sRetrofit.create(AlarmInterface::class.java)
        alarmInterface.getAlarm(roomId, page).enqueue(object: Callback<GetAlarmResponse> {
            override fun onResponse(call: Call<GetAlarmResponse>, response: Response<GetAlarmResponse>) {
                view.onGetAlarmSuccess(response.body() as GetAlarmResponse)
            }

            override fun onFailure(call: Call<GetAlarmResponse>, t: Throwable) {
                view.onGetAlarmFailure(t.message ?: "통신 오류")
            }
        })
    }
}