package com.example.nm1.src.register

import com.example.nm1.config.ApplicationClass
import com.example.nm1.config.BaseResponse
import com.example.nm1.src.register.model.PostUserSignUpRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterTwoService(val view: RegisterTwoView) {
    fun tryPostUserSignUp(map: HashMap<String, RequestBody>, img: MultipartBody.Part){
        val registerRetrofitInterface = ApplicationClass.sRetrofit.create(RegisterRetrofitInterface::class.java)
        registerRetrofitInterface.postSignUp(map, img).enqueue(object: Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                view.postUserSignUpSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.postUserSignUpFailure(t.message ?: "통신오류")
            }

        })
    }
}