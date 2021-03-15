package com.example.nm1.src.register

import com.example.nm1.config.BaseResponse
import com.example.nm1.src.register.model.PostCodeAuthRequest
import com.example.nm1.src.register.model.PostEmailAuthRequest
import com.example.nm1.src.register.model.PostPhoneAuthRequest
import com.example.nm1.src.register.model.PostUserSignUpRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface RegisterRetrofitInterface {
    @POST("/phone")
    fun postPhoneAuth(@Body params: PostPhoneAuthRequest): Call<BaseResponse>

    @POST("/check/phone")
    fun postCodeAuth(@Body params: PostCodeAuthRequest): Call<BaseResponse>

    @POST("/check/email")
    fun postEmailAuth(@Body params: PostEmailAuthRequest): Call<BaseResponse>

    @Multipart
    @POST("/user")
    fun postSignUp(@PartMap map: HashMap<String, RequestBody>,
                    @Part img: MultipartBody.Part): Call<BaseResponse>
}