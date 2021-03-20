package com.example.nm1.src.main.home.nest.todo

import com.example.nm1.src.main.home.nest.todo.models.AddOneDayTodoResponse
import com.example.nm1.src.main.home.nest.todo.models.AddRepeatTodoResponse

interface TodoFragmentView {
//   할일추가
    fun onAddOneDayTodoSuccess(response: AddOneDayTodoResponse)
    fun onAddOneDayTodoFailure(message: String)

//   반복할일추가
    fun onAddRepeatTodoSuccess(response: AddRepeatTodoResponse)
    fun onAddRepeatTodoFailure(message: String)
}