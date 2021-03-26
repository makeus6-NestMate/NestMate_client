package com.example.nm1.src.main.home.nest.notice.vote

import com.example.nm1.config.ApplicationClass
import com.example.nm1.config.BaseResponse
import com.example.nm1.src.main.home.nest.notice.vote.model.GetVoteResponse
import com.example.nm1.src.main.home.nest.notice.vote.model.PatchVoteRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VoteFragmentService(val view: VoteFragmentView) {

    fun tryGetVote(roomId: Int, voteId: Int){
        val retrofit = ApplicationClass.sRetrofit.create(VoteFragmentInterface::class.java)
        retrofit.getVote(roomId, voteId).enqueue(object: Callback<GetVoteResponse>{
            override fun onResponse(
                call: Call<GetVoteResponse>,
                response: Response<GetVoteResponse>
            ) {
                view.onGetVoteSuccess(response.body() as GetVoteResponse)
            }

            override fun onFailure(call: Call<GetVoteResponse>, t: Throwable) {
                view.onGetVoteFailure(t.message ?: "통신 오류")
            }

        })
    }

    fun tryPatchVote(roomId: Int, voteId: Int, request: PatchVoteRequest){
        val retrofit = ApplicationClass.sRetrofit.create(VoteFragmentInterface::class.java)
        retrofit.patchVote(roomId, voteId, request).enqueue(object: Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                view.onPatchVoteSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onPatchVoteFailure(t.message ?: "투표 종료")
            }

        })
    }

    fun tryPatchVoteFinish(roomId: Int, voteId: Int){
        val retrofit = ApplicationClass.sRetrofit.create(VoteFragmentInterface::class.java)
        retrofit.patchVoteFinish(roomId, voteId).enqueue(object: Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                view.onPatchVoteFinishSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onPatchVoteFinishFailure(t.message ?: "투표 종료")
            }

        })
    }
}