package com.example.nm1.src.login

import com.example.nm1.src.login.model.LoginResponse
import com.example.nm1.src.login.model.PostLoginRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginRetrofitInterface {
    @POST("/login")
    fun postLogin(@Body params: PostLoginRequest): Call<LoginResponse>
}