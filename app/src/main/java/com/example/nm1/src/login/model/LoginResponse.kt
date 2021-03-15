package com.example.nm1.src.login.model

import com.example.nm1.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class LoginResponse(@SerializedName("result") val result: ResultLogin): BaseResponse()
