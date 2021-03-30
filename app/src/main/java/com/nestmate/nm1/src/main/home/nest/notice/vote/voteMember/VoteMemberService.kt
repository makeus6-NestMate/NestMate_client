package com.nestmate.nm1.src.main.home.nest.notice.vote.voteMember

import com.nestmate.nm1.config.ApplicationClass
import com.nestmate.nm1.src.main.home.nest.notice.vote.voteMember.model.GetVoteMemberResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VoteMemberService(val view: VoteMemberView) {
    fun tryGetVoteMember(roomId: Int, voteId: Int, choiceId: Int){
        val retrofit = ApplicationClass.sRetrofit.create(VoteMemberInterface::class.java)
        retrofit.getVoteMember(roomId, voteId, choiceId).enqueue(object: Callback<GetVoteMemberResponse> {
            override fun onResponse(
                call: Call<GetVoteMemberResponse>,
                response: Response<GetVoteMemberResponse>
            ) {
                view.onGetVoteMemberSuccess(response.body() as GetVoteMemberResponse)
            }

            override fun onFailure(call: Call<GetVoteMemberResponse>, t: Throwable) {
                view.onGetVoteMemberFailure(t.message ?:"통신 오류")
            }

        })
    }
}