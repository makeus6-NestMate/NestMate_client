package com.example.nm1.src.login

import com.example.nm1.src.login.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginRetrofitInterface {
//    @POST("/login")
//    fun postLogin(@Body params: PostLoginRequest): Call<LoginResponse>

//   카카오 회원가입
    @POST("/kakao/user")
    fun postKakaoRegister(@Body params: PostKakaoRegisterRequest): Call<KakaoRegisterResponse>

//    카카오 로그인
    @POST("/kakao/user")
    fun postKakaoLogin(@Body params: PostKakaoLoginRequest): Call<KakaoLoginResponse>
}