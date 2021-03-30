package com.nestmate.nm1.src.main.home

import com.nestmate.nm1.config.ApplicationClass
import com.nestmate.nm1.src.main.home.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeService(val view: HomeView) {
    fun tryAddNest(postAddNestRequest: PostAddNestRequest){
       val homeInterface = ApplicationClass.sRetrofit.create(HomeInterface::class.java)
        homeInterface.postAddNest(postAddNestRequest).enqueue(object: Callback<AddNestResponse> {
           override fun onResponse(call: Call<AddNestResponse>, response: Response<AddNestResponse>) {
               view.onAddNestSuccess(response.body() as AddNestResponse)
           }

           override fun onFailure(call: Call<AddNestResponse>, t: Throwable) {
               view.onAddNestFailure(t.message ?: "통신 오류")
           }
       })
    }

    fun tryGetNest(page:Int){
        val homeInterface = ApplicationClass.sRetrofit.create(HomeInterface::class.java)
        homeInterface.getNest(page).enqueue(object: Callback<GetNestResponse> {
            override fun onResponse(call: Call<GetNestResponse>, response: Response<GetNestResponse>) {
                view.onGetNestSuccess(response.body() as GetNestResponse)
            }

            override fun onFailure(call: Call<GetNestResponse>, t: Throwable) {
                view.onGetNestFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPutNest(roomId:Int, putEditNestRequest: PutEditNestRequest){
        val homeInterface = ApplicationClass.sRetrofit.create(HomeInterface::class.java)
        homeInterface.putEditNest(roomId, putEditNestRequest).enqueue(object: Callback<PutEditNestResponse> {
            override fun onResponse(call: Call<PutEditNestResponse>, response: Response<PutEditNestResponse>) {
                view.onPutNestSuccess(response.body() as PutEditNestResponse)
            }

            override fun onFailure(call: Call<PutEditNestResponse>, t: Throwable) {
                view.onPutNestFailure(t.message ?: "통신 오류")
            }
        })
    }
}