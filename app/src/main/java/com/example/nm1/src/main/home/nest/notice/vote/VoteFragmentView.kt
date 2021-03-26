package com.example.nm1.src.main.home.nest.notice.vote

import com.example.nm1.config.BaseResponse
import com.example.nm1.src.main.home.nest.notice.vote.model.GetVoteResponse

interface VoteFragmentView {
    fun onGetVoteSuccess(response: GetVoteResponse)
    fun onGetVoteFailure(message: String)

    fun onPatchVoteSuccess(response: BaseResponse)
    fun onPatchVoteFailure(message: String)

    fun onPatchVoteFinishSuccess(response: BaseResponse)
    fun onPatchVoteFinishFailure(message: String)
}