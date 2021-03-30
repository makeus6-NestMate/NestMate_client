package com.nestmate.nm1.src.main.home.nest.todo

import com.nestmate.nm1.src.main.home.nest.todo.model.*

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

//   오늘 할일 조회
    fun onGetTodayTodoSuccess(response:GetTodayTodoResponse)
    fun onGetTodayTodoFailure(message: String)
    
//   할일 완료
    fun onPostCompleteTodoSuccess(response:PostTodoCompleteResponse)
    fun onPostCompleteTodoFailure(message: String)

//    콕찌르기 멤버
    fun onGetCockMemberSuccess(response:GetCockMemberResponse)
    fun onGetCockMemberFailure(message: String)

    //    콕찌르기
    fun onPostCockSuccess(response:PostCockResponse)
    fun onPostCockFailure(message: String)

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

    //    하루 키워드 검색 조회
    fun onGetSearchOneDayTodoSuccess(response:GetSearchOneDayTodoResponse)
    fun onGetSearchOneDayTodoFailure(message: String)

    //    반복 할일 조회
    fun onGetSearchRepeatTodoSuccess(response:GetSearchRepeatTodoResponse)
    fun onGetSearchRepeatTodoFailure(message: String)

//    하루 날짜로 검색
    fun onGetSearchTodoByDateSuccess(response:GetSearchTodoByDateResponse)
    fun onGetSearchTodoByDateFailure(message: String)

    //    하루 전체삭제
    fun onDeleteAllOneDayTodoSuccess(response:DeleteAllOneDayTodoResponse)
    fun onDeleteAllOneDayTodoFailure(message: String)

    //    반복 전체삭제
    fun onDeleteAllRepeatTodoSuccess(response:DeleteAllRepeatTodoResponse)
    fun onDeleteAllRepeatTodoFailure(message: String)
}