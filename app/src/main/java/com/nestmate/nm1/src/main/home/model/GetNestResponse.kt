package com.nestmate.nm1.src.main.home.model

import com.nestmate.nm1.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class GetNestResponse(@SerializedName("result") val result: ResultGetNest): BaseResponse()
