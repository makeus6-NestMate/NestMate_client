package com.example.nm1.src.main.home.nest.todo.model

import com.example.nm1.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class GetCockMemberResponse(@SerializedName("result") val result: ResultGetCockMember): BaseResponse()
