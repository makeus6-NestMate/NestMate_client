package com.example.nm1.src.main.home.nest.notice.vote.voteMember

import com.example.nm1.config.BaseResponse
import com.example.nm1.src.main.home.nest.notice.vote.voteMember.model.GetVoteMemberResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface VoteMemberInterface {
    @GET("/room/{roomId}/vote/{voteId}/choice/{choiceId}")
    fun getVoteMember(@Path("roomId") roomId: Int, @Path("voteId") voteId: Int, @Path("choiceId") choiceId: Int): Call<GetVoteMemberResponse>
}