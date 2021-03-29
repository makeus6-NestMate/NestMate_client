package com.example.nm1.src.login

import com.example.nm1.config.BaseResponse
import com.example.nm1.src.login.model.KakaoLoginResponse
import com.example.nm1.src.login.model.KakaoRegisterResponse
import com.example.nm1.src.login.model.LoginResponse

interface LoginActivityView {
//    fun onPostLoginSuccess(response: LoginResponse)
//    fun onPostLoginFailure(message: String)

    fun onPostKakaoRegisterSuccess(response: BaseResponse)
    fun onPostKakaoRegisterFailure(message: String)

    fun onPostKakaoLoginSuccess(response: KakaoLoginResponse)
    fun onPostKakaoLoginFailure(message: String)
}