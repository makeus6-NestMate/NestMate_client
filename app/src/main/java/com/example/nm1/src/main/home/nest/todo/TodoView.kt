package com.example.nm1.src.main.home.nest.todo

import com.example.nm1.src.main.home.nest.todo.model.*

interface TodoView {
//   할일추가
    fun onAddOneDayTodoSuccess(response: AddOneDayTodoResponse)
    fun onAddOneDayTodoFailure(message: String)

//   반복할일추가
    fun onAddRepeatTodoSuccess(response: AddRepeatTodoResponse)
    fun onAddRepeatTodoFailure(message: String)

//    하루 할일 조회
    fun onGetOneDayTodoSuccess(response:GetOneDayTodoResponse)
    fun onGetOneDayTodoFailure(message: String)

//    반복 할일 조회
    fun onGetRepeatTodoSuccess(response:GetRepeatTodoResponse)
    fun onGetRepeatTodoFailure(message: String)

    //    하루 할일 수정
    fun onPutOneDayTodoSuccess(response:PutOneDayTodoResponse)
    fun onPutOneDayTodoFailure(message: String)

    //    반복 할일 수정
    fun onPutRepeatTodoSuccess(response:PutRepeatTodoResponse)
    fun onPutRepeatTodoFailure(message: String)

//    하루 할일 삭제
    fun onDeleteOneDayTodoSuccess(response:DeleteOneDayTodoResponse)
    fun onDeleteOneDayTodoFailure(message: String)

//    반복 할일 삭제
    fun onDeleteRepeatTodoSuccess(response:DeleteRepeatTodoResponse)
    fun onDeleteRepeatTodoFailure(message: String)

}