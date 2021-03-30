package com.nestmate.nm1.src.main.mypage

import com.nestmate.nm1.src.main.mypage.model.GetProfileResponse
import retrofit2.Call
import retrofit2.http.GET

interface MyPageInterface {
    @GET("/user/profile")
    fun getProfile(): Call<GetProfileResponse>
}