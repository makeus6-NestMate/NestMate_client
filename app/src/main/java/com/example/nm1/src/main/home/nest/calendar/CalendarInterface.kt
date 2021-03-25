package com.example.nm1.src.main.home.nest.calendar

import com.example.nm1.src.main.home.nest.calendar.model.*
import retrofit2.Call
import retrofit2.http.*

interface CalendarInterface {
    //   일정 추가
    @POST("/room/{roomId}/calendar")
    fun postAddCalendar(@Path("roomId") roomId:Int, @Body params: PostAddCalendarRequest): Call<AddCalendarResponse>

    // 일정 삭제
    @DELETE("/room/{roomId}/calendar/{calendarId}")
    fun deleteCalendar(@Path("roomId") roomId:Int, @Path("calendarId") calendarId:Int): Call<DeleteCalendarResponse>

    // 일정 수정
    @PUT("/room/{roomId}/calendar/{calendarId}")
    fun putCalendar(@Path("roomId") roomId:Int, @Path("calendarId") calendarId:Int, @Body params: PutCalendarRequest): Call<PutCalendarResponse>

    // 일정 가져오기
    @GET("/room/{roomId}/calendar")
    fun getCalendar(@Path("roomId") roomId:Int, @Query("date") date:String) : Call<GetCalendarResponse>

    // 일정 세부 가져오기
    @GET("/room/{roomId}/calendar/detail")
    fun getDetailCalendar(@Path("roomId") roomId:Int, @Query("date") date:String) : Call<GetDetailCalendarResponse>
}