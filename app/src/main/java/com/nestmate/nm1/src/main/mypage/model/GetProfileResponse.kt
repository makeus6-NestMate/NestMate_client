package com.nestmate.nm1.src.main.mypage.model

import com.nestmate.nm1.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class GetProfileResponse(@SerializedName("result") val result: ResultProfile): BaseResponse()
