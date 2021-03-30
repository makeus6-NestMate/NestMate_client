package com.nestmate.nm1.src.main.home.nest.notice.vote.model

import com.nestmate.nm1.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class GetVoteResponse(@SerializedName("result") val result: ResultVote): BaseResponse()
