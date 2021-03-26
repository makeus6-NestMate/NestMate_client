package com.example.nm1.src.main.home.nest.notice.model

import com.example.nm1.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class GetNoticeVoteResponse(@SerializedName("result") val result: ResultNoticeVote):BaseResponse()
