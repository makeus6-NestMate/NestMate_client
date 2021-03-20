package com.example.nm1.src.main.home.nest.todo

import com.example.nm1.src.main.home.nest.todo.models.*
import retrofit2.Call
import retrofit2.http.*

interface TodoInterface {
//    할일 추가
    @POST("/todo/day")
    fun postAddOneDayTodo(@Body params: PostAddOneDayTodo): Call<AddOneDayTodoResponse>

//    반복 할일 추가
    @POST("/todo/days")
    fun postAddRepeatTodo(@Body params: PostAddRepeatTodo): Call<AddRepeatTodoResponse>

//    하루 할일 조회
    @GET("/room/{roomId}/todo/day")
    fun getOneDayTodo(@Query("roomId") roomId:Int)

//    반복 할일 조회
    @GET("/room/{roomId}/todo/days")
    fun getRepeatTodo(@Query("roomId") roomId:Int)

//    하루 할일 수정
    @PUT("/room/{roomId}/todo/day")
    fun putOneDayTodo(@Query("roomId") roomId:Int, @Body params:PutOneDayTodo) : Call<PutOneDayTodoResponse>

    //   반복 할일 수정
    @PUT("/room/{roomId}/todo/days")
    fun putRepeatTodo(@Query("roomId") roomId:Int, @Body params:PutRepeatTodo) : Call<PutRepeatTodoResponse>

//    하루 할일 삭제
    @DELETE("/room/{roomId}/todo/{todoId}/day")
    fun deleteOneDayTodo(@Query("roomId") roomId:Int, @Query("todoId") todoId:Int)

//    반복 할일 삭제
    @DELETE("/room/{roomId}/todo/{todoId}/days")
    fun deleteRepeatTodo(@Query("roomId") roomId:Int, @Query("todoId") todoId:Int)
}