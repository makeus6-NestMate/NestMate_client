package com.example.nm1.src.main.mypage.profile

import com.example.nm1.config.BaseResponse

interface ProfileView {
    fun onPutProfileSuccess(response: BaseResponse)
    fun onPutProfileFailure(message: String)
}