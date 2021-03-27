package com.example.nm1.src.main.mypage.model

import com.example.nm1.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class GetProfileResponse(@SerializedName("result") val result: ResultProfile): BaseResponse()
