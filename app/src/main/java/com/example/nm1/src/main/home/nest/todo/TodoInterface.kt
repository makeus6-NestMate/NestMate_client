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

//    오늘 할일 조회
    @GET("/room/{roomId}/todo/today")
    fun getTodayTodo(@Path("roomId") roomId:Int) : Call<GetTodayTodoResponse>

//    오늘 할일 완료
    @POST("/room/{roomId}/todo/{todoId}/complete")
    fun postTodoComplete(@Path("roomId") roomId:Int, @Path("todoId") todoId: Int): Call<PostTodoCompleteResponse>

//    콕찌르기
    @POST("/room/{roomId}/todo/{todoId}/member/{memberId}")
    fun postCock(@Path("roomId") roomId:Int, @Path("todoId") todoId: Int, @Path("memberId") memberId:Int): Call<PostCockResponse>

//    콕찌르기 멤버 가져오기
    @GET("/room/{roomId}/todo/member")
    fun getCockMember(@Path("roomId") roomId:Int) : Call<GetCockMemberResponse>

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

//   하루할일 키워드검색
    @GET("/room/{roomId}/todo/day/search")
    fun getSearchOneDayTodo(@Path("roomId") roomId:Int, @Query("keyword") keyword:String) : Call<GetSearchOneDayTodoResponse>

//    반복 할일 키워드검색
    @GET("/room/{roomId}/todo/days/search")
    fun getSearchRepeatTodo(@Path("roomId") roomId:Int, @Query("keyword") keyword:String) : Call<GetSearchRepeatTodoResponse>

//    하루할일 날짜 검색
    @GET("/room/{roomId}/todo/day/calendar")
    fun getSearchTodoByDate(@Path("roomId") roomId:Int, @Query("date") date:String) : Call<GetSearchTodoByDateResponse>

//    하루할일 전체 삭제
    @DELETE("/room/{roomId}/todo/day")
    fun deleteAllOneDayTodo(@Path("roomId") roomId:Int) : Call<DeleteAllOneDayTodoResponse>

    //  반복할일 전체 삭제
    @DELETE("/room/{roomId}/todo/days")
    fun deleteAllRepeatTodo(@Path("roomId") roomId:Int) : Call<DeleteAllRepeatTodoResponse>
}