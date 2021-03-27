package com.example.nm1.src.main.mypage

import com.example.nm1.config.ApplicationClass
import com.example.nm1.src.main.mypage.model.GetProfileResponse
import retrofit2.Call
import retrofit2.http.GET

interface MyPageInterface {
    @GET("/user/profile")
    fun getProfile(): Call<GetProfileResponse>
}