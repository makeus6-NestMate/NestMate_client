package com.example.nm1.src.main.mypage

import com.example.nm1.config.ApplicationClass
import com.example.nm1.src.main.mypage.model.GetProfileResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageService(val view: MyPageView) {

    fun tryGetProfile(){
        val retrofit = ApplicationClass.sRetrofit.create(MyPageInterface::class.java)
        retrofit.getProfile().enqueue(object: Callback<GetProfileResponse>{
            override fun onResponse(
                call: Call<GetProfileResponse>,
                response: Response<GetProfileResponse>
            ) {
                view.onGetProfileSuccess(response.body() as GetProfileResponse)
            }

            override fun onFailure(call: Call<GetProfileResponse>, t: Throwable) {
                view.onGetProfileFailure(t.message ?: "통신 오류")
            }

        })
    }
}