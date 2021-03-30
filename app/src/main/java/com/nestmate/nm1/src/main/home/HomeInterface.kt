package com.nestmate.nm1.src.main.home

import com.nestmate.nm1.src.main.home.model.*
import retrofit2.Call
import retrofit2.http.*

interface HomeInterface {
//   둥지 만들기
    @POST("/room")
    fun postAddNest(@Body params: PostAddNestRequest): Call<AddNestResponse>

//    둥지 가져오기
    @GET("/room")
    fun getNest(@Query("page") page:Int): Call<GetNestResponse>

//    둥지 수정
    @PUT("/room/{roomId}")
    fun putEditNest(@Path("roomId") roomId:Int, @Body params: PutEditNestRequest): Call<PutEditNestResponse>
}