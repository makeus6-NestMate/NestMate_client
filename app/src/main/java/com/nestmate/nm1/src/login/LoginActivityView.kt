package com.nestmate.nm1.src.login

import com.nestmate.nm1.config.BaseResponse
import com.nestmate.nm1.src.login.model.KakaoLoginResponse

interface LoginActivityView {
//    fun onPostLoginSuccess(response: LoginResponse)
//    fun onPostLoginFailure(message: String)

    fun onPostKakaoRegisterSuccess(response: BaseResponse)
    fun onPostKakaoRegisterFailure(message: String)

    fun onPostKakaoLoginSuccess(response: KakaoLoginResponse)
    fun onPostKakaoLoginFailure(message: String)
}