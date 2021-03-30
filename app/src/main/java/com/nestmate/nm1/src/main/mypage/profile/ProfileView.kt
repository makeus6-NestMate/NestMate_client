package com.nestmate.nm1.src.main.mypage.profile

import com.nestmate.nm1.config.BaseResponse

interface ProfileView {
    fun onPutProfileSuccess(response: BaseResponse)
    fun onPutProfileFailure(message: String)
}