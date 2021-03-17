package com.example.nm1.src.main.home

import com.example.nm1.src.main.home.model.AddNestResponse
import com.example.nm1.src.main.home.model.GetNestResponse
import com.example.nm1.src.main.home.model.PostAddNestRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface HomeInterface {
//   둥지 만들기
    @POST("/room")
    fun postAddNest(@Body params: PostAddNestRequest): Call<AddNestResponse>

//    둥지 가져오기
    @GET("/room")
    fun getNest(): Call<GetNestResponse>
}