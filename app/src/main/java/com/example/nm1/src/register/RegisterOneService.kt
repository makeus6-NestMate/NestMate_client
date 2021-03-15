package com.example.nm1.src.register

import com.example.nm1.config.ApplicationClass
import com.example.nm1.config.BaseResponse
import com.example.nm1.src.register.model.PostCodeAuthRequest
import com.example.nm1.src.register.model.PostEmailAuthRequest
import com.example.nm1.src.register.model.PostPhoneAuthRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterOneService(val view: RegisterOneView) {
    fun tryPostPhoneAuth(phoneAuthRequest: PostPhoneAuthRequest){
        val registerRetrofitInterface = ApplicationClass.sRetrofit.create(RegisterRetrofitInterface::class.java)
        registerRetrofitInterface.postPhoneAuth(phoneAuthRequest).enqueue(object:
            Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                view.onPostPhoneAuthSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
               view.onPostPhoneAuthFailure(t.message ?: "통신 오류")
            }

        })
    }

    fun tryPostCodeAuth(codeAuthRequest: PostCodeAuthRequest){
        val registerRetrofitInterface = ApplicationClass.sRetrofit.create(RegisterRetrofitInterface::class.java)
        registerRetrofitInterface.postCodeAuth(codeAuthRequest).enqueue(object: Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                view.onPostCodeAuthSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onPostCodeAuthFailure(t.message ?: "통신 오류")
            }

        })
    }

    fun tryPostEmailAuth(emailAuthRequest: PostEmailAuthRequest){
        val registerRetrofitInterface = ApplicationClass.sRetrofit.create(RegisterRetrofitInterface::class.java)
        registerRetrofitInterface.postEmailAuth(emailAuthRequest).enqueue(object: Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                view.onPostEmailAuthSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onPostEmailAuthFailure(t.message ?: "통신 오류")
            }

        })
    }
}