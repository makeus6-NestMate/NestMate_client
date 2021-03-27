package com.example.nm1.src.main.home.nest.notice

import com.example.nm1.config.BaseResponse
import com.example.nm1.src.main.home.nest.notice.model.GetNoticeVoteResponse
import com.example.nm1.src.main.home.nest.notice.model.PostNoticeRequest
import com.example.nm1.src.main.home.nest.notice.model.PostVoteRequest
import retrofit2.Call
import retrofit2.http.*

interface NoticeVoteInterface {

    @POST("/room/{roomId}/notice")
    fun postNotice(@Path("roomId") roomId: Int, @Body params: PostNoticeRequest): Call<BaseResponse>

    @GET("/room/{roomId}/noticeVote")
    fun getNoticeVote(@Path("roomId") roomId: Int, @Query("page") page: Int): Call<GetNoticeVoteResponse>

    @POST("/room/{roomId}/vote")
    fun postVote(@Path("roomId") roomId: Int, @Body params: PostVoteRequest): Call<BaseResponse>

    @DELETE("/room/{roomId}/notice/{noticeId}")
    fun deleteNotice(@Path("roomId") roomId: Int, @Path("noticeId") noticeId: Int): Call<BaseResponse>

    @DELETE("/room/{roomId}/vote/{voteId}")
    fun deleteVote(@Path("roomId") roomId: Int, @Path("voteId") voteId: Int): Call<BaseResponse>
}