package com.example.nm1.src.main.home

import com.example.nm1.config.ApplicationClass
import com.example.nm1.src.main.home.model.AddNestResponse
import com.example.nm1.src.main.home.model.PostAddNestRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeService(val view: HomeFragmentView) {
    fun tryAddNest(postAddNestRequest: PostAddNestRequest){
       val loginRetrofitInterface = ApplicationClass.sRetrofit.create(HomeInterface::class.java)
       loginRetrofitInterface.postLogin(postAddNestRequest).enqueue(object: Callback<AddNestResponse> {
           override fun onResponse(call: Call<AddNestResponse>, response: Response<AddNestResponse>) {
               view.onAddNestSuccess(response.body() as AddNestResponse)
           }

           override fun onFailure(call: Call<AddNestResponse>, t: Throwable) {
               view.onAddNestFailure(t.message ?: "통신 오류")
           }
       })
    }
}