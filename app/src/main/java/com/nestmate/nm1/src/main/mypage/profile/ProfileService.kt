package com.nestmate.nm1.src.main.mypage.profile

import com.nestmate.nm1.config.ApplicationClass
import com.nestmate.nm1.config.BaseResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileService(val view: ProfileView) {
    fun tryPutProfile(map: HashMap<String, RequestBody>?, img: MultipartBody.Part?){
        val retrofit = ApplicationClass.sRetrofit.create(ProfileInterface::class.java)
        retrofit.putProfile(map, img).enqueue(object: Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                view.onPutProfileSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onPutProfileFailure(t.message ?:"통신 오류")
            }

        })
    }
}