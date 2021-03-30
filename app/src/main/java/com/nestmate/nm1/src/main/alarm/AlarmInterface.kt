package com.nestmate.nm1.src.main.alarm

import com.nestmate.nm1.src.main.alarm.model.GetAlarmResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AlarmInterface {
    @GET("/room/{roomId}/alarm")
    fun getAlarm(@Path("roomId") roomId:Int, @Query("page") page:Int): Call<GetAlarmResponse>
}