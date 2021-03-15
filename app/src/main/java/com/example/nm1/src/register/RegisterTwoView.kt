package com.example.nm1.src.register

import com.example.nm1.config.BaseResponse

interface RegisterTwoView {
    fun postUserSignUpSuccess(response: BaseResponse)
    fun postUserSignUpFailure(message: String)
}