package com.example.nm1.src.main.home.nest.member.model

import com.example.nm1.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class GetMemberResponse(@SerializedName("result") val result: ResultGetMember): BaseResponse()
