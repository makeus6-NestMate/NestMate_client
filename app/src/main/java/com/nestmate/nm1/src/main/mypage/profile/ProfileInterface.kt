package com.nestmate.nm1.src.main.mypage.profile

import com.nestmate.nm1.config.BaseResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.PartMap

interface ProfileInterface {
    @Multipart
    @PUT("/user/profile")
    fun putProfile(@PartMap map: HashMap<String, RequestBody>? = null, @Part img: MultipartBody.Part? = null): Call<BaseResponse>

}