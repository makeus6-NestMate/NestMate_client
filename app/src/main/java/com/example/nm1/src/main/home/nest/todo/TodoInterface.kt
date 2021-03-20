package com.example.nm1.src.main.home.nest.todo

import com.example.nm1.src.main.home.model.AddNestResponse
import com.example.nm1.src.main.home.model.GetNestResponse
import com.example.nm1.src.main.home.model.PostAddNestRequest
import com.example.nm1.src.main.home.nest.todo.models.AddOneDayTodoResponse
import com.example.nm1.src.main.home.nest.todo.models.AddRepeatTodoResponse
import com.example.nm1.src.main.home.nest.todo.models.PostAddOneDayTodo
import com.example.nm1.src.main.home.nest.todo.models.PostAddRepeatTodo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TodoInterface {
//    할일 추가
    @POST("/todo/day")
    fun postAddOneDayTodo(@Body params: PostAddOneDayTodo): Call<AddOneDayTodoResponse>

//    반복 할일 추가
    @POST("/todo/days")
    fun postAddRepeatTodo(@Body params: PostAddRepeatTodo): Call<AddRepeatTodoResponse>
}