package com.nestmate.nm1.src.main.mypage

import com.nestmate.nm1.src.main.mypage.model.GetProfileResponse

interface MyPageView {

    fun onGetProfileSuccess(response: GetProfileResponse)
    fun onGetProfileFailure(message: String)
}