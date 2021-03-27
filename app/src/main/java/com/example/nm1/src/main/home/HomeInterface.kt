package com.example.nm1.src.main.home

import com.example.nm1.src.main.home.model.*
import retrofit2.Call
import retrofit2.http.*

interface HomeInterface {
//   둥지 만들기
    @POST("/room")
    fun postAddNest(@Body params: PostAddNestRequest): Call<AddNestResponse>

//    둥지 가져오기
    @GET("/room?page=0")
    fun getNest(): Call<GetNestResponse>

//    둥지 수정
    @PUT("/room/{roomId}")
    fun putEditNest(@Path("roomId") roomId:Int, @Body params: PutEditNestRequest): Call<PutEditNestResponse>
}