package com.example.nm1.src.main.home.nest.todo

import com.example.nm1.config.ApplicationClass
import com.example.nm1.src.main.home.model.AddNestResponse
import com.example.nm1.src.main.home.model.GetNestResponse
import com.example.nm1.src.main.home.model.PostAddNestRequest
import com.example.nm1.src.main.home.nest.todo.models.AddOneDayTodoResponse
import com.example.nm1.src.main.home.nest.todo.models.AddRepeatTodoResponse
import com.example.nm1.src.main.home.nest.todo.models.PostAddOneDayTodo
import com.example.nm1.src.main.home.nest.todo.models.PostAddRepeatTodo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TodoService(val view: TodoFragmentView) {
    fun tryAddOneDayTodo(postAddOneDayTodo: PostAddOneDayTodo){
       val todoInterface = ApplicationClass.sRetrofit.create(TodoInterface::class.java)
        todoInterface.postAddOneDayTodo(postAddOneDayTodo).enqueue(object: Callback<AddOneDayTodoResponse> {
           override fun onResponse(call: Call<AddOneDayTodoResponse>, response: Response<AddOneDayTodoResponse>) {
               view.onAddOneDayTodoSuccess(response.body() as AddOneDayTodoResponse)
           }

           override fun onFailure(call: Call<AddOneDayTodoResponse>, t: Throwable) {
               view.onAddOneDayTodoFailure(t.message ?: "통신 오류")
           }
       })
    }

    fun tryAddRepeatTodo(postAddRepeatTodo: PostAddRepeatTodo){
        val todoInterface = ApplicationClass.sRetrofit.create(TodoInterface::class.java)
        todoInterface.postAddRepeatTodo(postAddRepeatTodo).enqueue(object: Callback<AddRepeatTodoResponse> {
            override fun onResponse(call: Call<AddRepeatTodoResponse>, response: Response<AddRepeatTodoResponse>) {
                view.onAddRepeatTodoSuccess(response.body() as AddRepeatTodoResponse)
            }

            override fun onFailure(call: Call<AddRepeatTodoResponse>, t: Throwable) {
                view.onAddRepeatTodoFailure(t.message ?: "통신 오류")
            }
        })
    }
}