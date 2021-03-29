package com.example.nm1.src.main.home.nest.calendar

import com.example.nm1.config.ApplicationClass
import com.example.nm1.src.main.home.HomeInterface
import com.example.nm1.src.main.home.model.GetNestResponse
import com.example.nm1.src.main.home.nest.calendar.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CalendarService (val view: CalendarActivityView) {
    fun tryAddCalendar(roomId:Int, postAddCalendarRequest: PostAddCalendarRequest){
        val calendarInterface = ApplicationClass.sRetrofit.create(CalendarInterface::class.java)
        calendarInterface.postAddCalendar(roomId, postAddCalendarRequest).enqueue(object: Callback<AddCalendarResponse> {
            override fun onResponse(call: Call<AddCalendarResponse>, response: Response<AddCalendarResponse>) {
                view.onAddCalendarSuccess(response.body() as AddCalendarResponse)
            }

            override fun onFailure(call: Call<AddCalendarResponse>, t: Throwable) {
                view.onAddCalendarFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPutCalendar(roomId:Int, calendarId:Int, putCalendarRequest: PutCalendarRequest){
        val calendarInterface = ApplicationClass.sRetrofit.create(CalendarInterface::class.java)
        calendarInterface.putCalendar(roomId, calendarId, putCalendarRequest).enqueue(object: Callback<PutCalendarResponse> {
            override fun onResponse(call: Call<PutCalendarResponse>, response: Response<PutCalendarResponse>) {
                view.onPutCalendarSuccess(response.body() as PutCalendarResponse)
            }

            override fun onFailure(call: Call<PutCalendarResponse>, t: Throwable) {
                view.onPutCalendarFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryDeleteCalendar(roomId:Int, calendarId:Int){
        val calendarInterface = ApplicationClass.sRetrofit.create(CalendarInterface::class.java)
        calendarInterface.deleteCalendar(roomId, calendarId).enqueue(object: Callback<DeleteCalendarResponse> {
            override fun onResponse(call: Call<DeleteCalendarResponse>, response: Response<DeleteCalendarResponse>) {
                view.onDeleteCalendarSuccess(response.body() as DeleteCalendarResponse)
            }

            override fun onFailure(call: Call<DeleteCalendarResponse>, t: Throwable) {
                view.onDeleteCalendarFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetCalendar(roomId:Int, date:String){
        val calendarInterface = ApplicationClass.sRetrofit.create(CalendarInterface::class.java)
        calendarInterface.getCalendar(roomId, date).enqueue(object: Callback<GetCalendarResponse> {
            override fun onResponse(call: Call<GetCalendarResponse>, response: Response<GetCalendarResponse>) {
                view.onGetCalendarSuccess(response.body() as GetCalendarResponse)
            }

            override fun onFailure(call: Call<GetCalendarResponse>, t: Throwable) {
                view.onGetCalendarFailure(t.message ?: "통신 오류")
            }
        })
    }


    fun tryGetDetailCalendar(roomId:Int, date:String, page:Int){
        val calendarInterface = ApplicationClass.sRetrofit.create(CalendarInterface::class.java)
        calendarInterface.getDetailCalendar(roomId, date, page).enqueue(object: Callback<GetDetailCalendarResponse> {
            override fun onResponse(call: Call<GetDetailCalendarResponse>, response: Response<GetDetailCalendarResponse>) {
                view.onGetDetailCalendarSuccess(response.body() as GetDetailCalendarResponse)
            }

            override fun onFailure(call: Call<GetDetailCalendarResponse>, t: Throwable) {
                view.onGetDetailCalendarFailure(t.message ?: "통신 오류")
            }
        })
    }
}