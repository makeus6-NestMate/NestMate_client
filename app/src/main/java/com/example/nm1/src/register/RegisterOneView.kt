package com.example.nm1.src.register

import com.example.nm1.config.BaseResponse

interface RegisterOneView {
    fun onPostPhoneAuthSuccess(response: BaseResponse)
    fun onPostPhoneAuthFailure(message: String)

    fun onPostCodeAuthSuccess(response: BaseResponse)
    fun onPostCodeAuthFailure(message: String)

    fun onPostEmailAuthSuccess(response: BaseResponse)
    fun onPostEmailAuthFailure(message: String)
}