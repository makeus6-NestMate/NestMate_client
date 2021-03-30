package com.nestmate.nm1.src.main.home.nest.notice.vote.voteMember.model

import com.nestmate.nm1.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class GetVoteMemberResponse(@SerializedName("result") val result: ResultVoteMember): BaseResponse()
