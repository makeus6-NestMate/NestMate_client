package com.example.nm1.src.main.home

import com.example.nm1.src.main.home.model.AddNestResponse
import com.example.nm1.src.main.home.model.PostAddNestRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface HomeInterface {
    @POST("/room")
    fun postLogin(@Body params: PostAddNestRequest): Call<AddNestResponse>
}