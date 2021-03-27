package com.example.nm1.src.main.mypage

import com.example.nm1.src.main.mypage.model.GetProfileResponse

interface MyPageView {

    fun onGetProfileSuccess(response: GetProfileResponse)
    fun onGetProfileFailure(message: String)
}