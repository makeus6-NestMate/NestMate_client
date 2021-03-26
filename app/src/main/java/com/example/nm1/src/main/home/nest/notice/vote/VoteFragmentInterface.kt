package com.example.nm1.src.main.home.nest.notice.vote

import com.example.nm1.config.BaseResponse
import com.example.nm1.src.main.home.nest.notice.vote.model.GetVoteResponse
import com.example.nm1.src.main.home.nest.notice.vote.model.PatchVoteRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface VoteFragmentInterface {
    @GET("/room/{roomId}/vote/{voteId}")
    fun getVote(@Path("roomId") roomId: Int, @Path("voteId") voteId: Int): Call<GetVoteResponse>

    @PATCH("/room/{roomId}/vote/{voteId}")
    fun patchVote(@Path("roomId") roomId: Int, @Path("voteId") voteId: Int, @Body params: PatchVoteRequest): Call<BaseResponse>

    @PATCH("/room/{roomId}/vote/{voteId}/complete")
    fun patchVoteFinish(@Path("roomId") roomId: Int, @Path("voteId") voteId: Int): Call<BaseResponse>
}