package com.example.nm1.src.main.home.nest.chart

import com.example.nm1.config.ApplicationClass
import com.example.nm1.src.main.home.nest.calendar.CalendarActivityView
import com.example.nm1.src.main.home.nest.calendar.CalendarInterface
import com.example.nm1.src.main.home.nest.calendar.model.AddCalendarResponse
import com.example.nm1.src.main.home.nest.calendar.model.PostAddCalendarRequest
import com.example.nm1.src.main.home.nest.chart.model.GetChartResponse
import com.example.nm1.src.main.home.nest.chart.model.PostChartClapResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChartService (val view: ChartActivityView) {
    fun tryPostChartClap(roomId:Int, memberId:Int){
        val chartInterface = ApplicationClass.sRetrofit.create(ChartInterface::class.java)
        chartInterface.postChartClap(roomId, memberId).enqueue(object: Callback<PostChartClapResponse> {
            override fun onResponse(call: Call<PostChartClapResponse>, response: Response<PostChartClapResponse>) {
                view.onPostChartClapSuccess(response.body() as PostChartClapResponse)
            }

            override fun onFailure(call: Call<PostChartClapResponse>, t: Throwable) {
                view.onPostChartClapFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetChart(roomId:Int){
        val chartInterface = ApplicationClass.sRetrofit.create(ChartInterface::class.java)
        chartInterface.getChart(roomId).enqueue(object: Callback<GetChartResponse> {
            override fun onResponse(call: Call<GetChartResponse>, response: Response<GetChartResponse>) {
                view.onGetChartSuccess(response.body() as GetChartResponse)
            }

            override fun onFailure(call: Call<GetChartResponse>, t: Throwable) {
                view.onGetChartFailure(t.message ?: "통신 오류")
            }
        })
    }
}