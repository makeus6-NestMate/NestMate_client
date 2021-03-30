package com.nestmate.nm1.src.login.model

import com.nestmate.nm1.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class KakaoLoginResponse(@SerializedName("result") val result: ResultKakaoLogin): BaseResponse()
