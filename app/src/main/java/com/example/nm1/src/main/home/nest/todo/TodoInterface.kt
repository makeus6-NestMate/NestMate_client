package com.example.nm1.src.main.home.nest.todo

import com.example.nm1.src.main.home.nest.todo.model.*
import retrofit2.Call
import retrofit2.http.*

interface TodoInterface {
//    할일 추가
    @POST("/room/{roomId}/todo/day")
    fun postAddOneDayTodo(@Path("roomId") roomId:Int, @Body params: PostAddOneDayTodo): Call<AddOneDayTodoResponse>

//    반복 할일 추가
    @POST("/room/{roomId}/todo/days")
    fun postAddRepeatTodo(@Path("roomId") roomId:Int, @Body params: PostAddRepeatTodo): Call<AddRepeatTodoResponse>

//    하루 할일 조회
    @GET("/room/{roomId}/todo/day")
    fun getOneDayTodo(@Path("roomId") roomId:Int) : Call<GetOneDayTodoResponse>

//    반복 할일 조회
    @GET("/room/{roomId}/todo/days")
    fun getRepeatTodo(@Path("roomId") roomId:Int) : Call<GetRepeatTodoResponse>

//    하루 할일 수정
    @PUT("/room/{roomId}/todo/day")
    fun putOneDayTodo(@Path("roomId") roomId:Int, @Body params:PutOneDayTodo) : Call<PutOneDayTodoResponse>

    //   반복 할일 수정
    @PUT("/room/{roomId}/todo/days")
    fun putRepeatTodo(@Path("roomId") roomId:Int, @Body params:PutRepeatTodo) : Call<PutRepeatTodoResponse>

//    하루 할일 삭제
    @DELETE("/room/{roomId}/todo/{todoId}/day")
    fun deleteOneDayTodo(@Path("roomId") roomId:Int, @Path("todoId") todoId:Int) : Call<DeleteOneDayTodoResponse>

//    반복 할일 삭제
    @DELETE("/room/{roomId}/todo/{todoId}/days")
    fun deleteRepeatTodo(@Path("roomId") roomId:Int, @Path("todoId") todoId:Int): Call<DeleteRepeatTodoResponse>

}