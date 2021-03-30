package com.nestmate.nm1.src.main.home.nest.notice.model

import com.nestmate.nm1.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class GetNoticeVoteResponse(@SerializedName("result") val result: ResultNoticeVote):BaseResponse()
