package com.nestmate.nm1.src.login.model

import com.nestmate.nm1.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class KakaoRegisterResponse(@SerializedName("result") val result:String?=null): BaseResponse()
