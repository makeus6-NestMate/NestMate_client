package com.example.nm1.src.main.home.nest.chart

import com.example.nm1.src.main.home.model.AddNestResponse
import com.example.nm1.src.main.home.model.GetNestResponse
import com.example.nm1.src.main.home.model.PostAddNestRequest
import com.example.nm1.src.main.home.nest.chart.model.GetChartResponse
import com.example.nm1.src.main.home.nest.chart.model.PostChartClapResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ChartInterface {
    @POST("/room/{roomId}/member/{memberId}/clap")
    fun postChartClap(@Path("roomId") roomId:Int, @Path("memberId") memberId:Int): Call<PostChartClapResponse>

    @GET("/room/{roomId}/chart")
    fun getChart(@Path("roomId") roomId:Int): Call<GetChartResponse>
}