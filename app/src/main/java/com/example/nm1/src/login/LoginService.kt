package com.example.nm1.src.login

import com.example.nm1.config.ApplicationClass
import com.example.nm1.src.login.model.LoginResponse
import com.example.nm1.src.login.model.PostLoginRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginService(val view: LoginActivityView) {
    fun tryPostLogin(postLoginRequest: PostLoginRequest){
       val loginRetrofitInterface = ApplicationClass.sRetrofit.create(LoginRetrofitInterface::class.java)
       loginRetrofitInterface.postLogin(postLoginRequest).enqueue(object: Callback<LoginResponse> {
           override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
               view.onPostLoginSuccess(response.body() as LoginResponse)
           }

           override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
               view.onPostLoginFailure(t.message ?: "통신 오류")
           }

       })
    }
}