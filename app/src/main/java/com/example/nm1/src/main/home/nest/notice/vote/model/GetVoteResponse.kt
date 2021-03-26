package com.example.nm1.src.main.home.nest.notice.vote.model

import com.example.nm1.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class GetVoteResponse(@SerializedName("result") val result: ResultVote): BaseResponse()
