package com.example.nm1.src.login

import com.example.nm1.config.ApplicationClass
import com.example.nm1.src.login.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginService(val view: LoginActivityView) {
//    fun tryPostLogin(postLoginRequest: PostLoginRequest){
//       val loginRetrofitInterface = ApplicationClass.sRetrofit.create(LoginRetrofitInterface::class.java)
//       loginRetrofitInterface.postLogin(postLoginRequest).enqueue(object: Callback<LoginResponse> {
//           override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
//               view.onPostLoginSuccess(response.body() as LoginResponse)
//           }
//
//           override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//               view.onPostLoginFailure(t.message ?: "통신 오류")
//           }
//
//       })
//    }

    fun tryPostKakaoRegister(map: HashMap<String, RequestBody>?, img: MultipartBody.Part?){
        val loginRetrofitInterface = ApplicationClass.sRetrofit.create(LoginRetrofitInterface::class.java)
        loginRetrofitInterface.postKakaoRegister(map, img).enqueue(object: Callback<KakaoRegisterResponse> {
            override fun onResponse(call: Call<KakaoRegisterResponse>, response: Response<KakaoRegisterResponse>) {
                view.onPostKakaoRegisterSuccess(response.body() as KakaoRegisterResponse)
            }

            override fun onFailure(call: Call<KakaoRegisterResponse>, t: Throwable) {
                view.onPostKakaoRegisterFailure(t.message ?: "통신 오류")
            }

        })
    }

    fun tryPostKakaoLogin(postKakaoLoginRequest: PostKakaoLoginRequest){
        val loginRetrofitInterface = ApplicationClass.sRetrofit.create(LoginRetrofitInterface::class.java)
        loginRetrofitInterface.postKakaoLogin(postKakaoLoginRequest).enqueue(object: Callback<KakaoLoginResponse> {
            override fun onResponse(call: Call<KakaoLoginResponse>, response: Response<KakaoLoginResponse>) {
                view.onPostKakaoLoginSuccess(response.body() as KakaoLoginResponse)
            }

            override fun onFailure(call: Call<KakaoLoginResponse>, t: Throwable) {
                view.onPostKakaoLoginFailure(t.message ?: "통신 오류")
            }

        })
    }
}