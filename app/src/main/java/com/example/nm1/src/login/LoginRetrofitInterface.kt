package com.example.nm1.src.login

import com.example.nm1.src.login.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface LoginRetrofitInterface {
//    @POST("/login")
//    fun postLogin(@Body params: PostLoginRequest): Call<LoginResponse>

//   카카오 회원가입
    @Multipart
    @POST("/kakao/user")
    fun postKakaoRegister(@Part("nickname") nickname:RequestBody,
                          @Part profileImg:MultipartBody.Part?=null,
                          @Part("email") email:RequestBody,
                          @Part("access_token") access_token:RequestBody,
                          @Part("kakaoImg") kakaoImg:RequestBody?=null): Call<KakaoRegisterResponse>

//    카카오 로그인
    @POST("/kakao/login")
    fun postKakaoLogin(@Body params: PostKakaoLoginRequest): Call<KakaoLoginResponse>
}