package com.example.nm1.src.main.alarm

import com.example.nm1.src.main.alarm.model.GetAlarmResponse
import com.example.nm1.src.main.home.nest.chart.model.GetChartResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AlarmInterface {
    @GET("/room/{roomId}/alarm")
    fun getAlarm(@Path("roomId") roomId:Int, @Query("page") page:Int): Call<GetAlarmResponse>
}