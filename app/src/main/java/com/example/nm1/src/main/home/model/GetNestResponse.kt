package com.example.nm1.src.main.home.model

import com.example.nm1.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class GetNestResponse(@SerializedName("result") val result: ResultGetNest): BaseResponse()
