package com.example.nm1.src.main.home.nest.notice.vote.voteMember.model

import com.example.nm1.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class GetVoteMemberResponse(@SerializedName("result") val result: ResultVoteMember): BaseResponse()
