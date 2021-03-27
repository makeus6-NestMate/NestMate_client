package com.example.nm1.src.main.home.nest.notice.vote.voteMember

import com.example.nm1.src.main.home.nest.notice.vote.voteMember.model.GetVoteMemberResponse

interface VoteMemberView {
    fun onGetVoteMemberSuccess(response: GetVoteMemberResponse)
    fun onGetVoteMemberFailure(message: String)
}