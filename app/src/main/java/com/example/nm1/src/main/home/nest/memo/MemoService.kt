package com.example.nm1.src.main.home.nest.memo

import com.example.nm1.config.ApplicationClass
import com.example.nm1.config.BaseResponse
import com.example.nm1.src.main.home.nest.memo.model.GetMemoResponse
import com.example.nm1.src.main.home.nest.memo.model.PatchMemoRequest
import com.example.nm1.src.main.home.nest.memo.model.PostMemoRequest
import com.example.nm1.src.main.home.nest.memo.model.PutMemoRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MemoService(val view: MemoView) {

    fun tryPostMemo(roomId: Int, postMemoRequest: PostMemoRequest){
        val retrofit = ApplicationClass.sRetrofit.create(MemoInterface::class.java)
        retrofit.postMemo(roomId, postMemoRequest).enqueue(object: Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                view.onPostMemoSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onPostMemoFailure(t.message ?: "통신 오류")
            }

        })
    }

    fun tryGetMemo(roomId: Int){
        val retrofit = ApplicationClass.sRetrofit.create(MemoInterface::class.java)
        retrofit.getMemo(roomId).enqueue(object: Callback<GetMemoResponse>{
            override fun onResponse(
                call: Call<GetMemoResponse>,
                response: Response<GetMemoResponse>
            ) {
                view.onGetMemoSuccess(response.body() as GetMemoResponse)
            }

            override fun onFailure(call: Call<GetMemoResponse>, t: Throwable) {
                view.onGetMemoFailure(t.message ?: "통신 오류")
            }

        })
    }

    fun tryDeleteMemo(roomId: Int, memoId: Int){
        val retrofit = ApplicationClass.sRetrofit.create(MemoInterface::class.java)
        retrofit.deleteMemo(roomId, memoId).enqueue(object: Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                view.onDeleteMemoSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onDeleteMemoFailure(t.message ?: "통신 오류")
            }

        })
    }

    fun tryPatchMemo(roomId: Int, memoId: Int, request: PatchMemoRequest){
        val retrofit = ApplicationClass.sRetrofit.create(MemoInterface::class.java)
        retrofit.patchMEmo(roomId, memoId, request).enqueue(object: Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                view.onPatchMemoSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onPatchMemoFailure(t.message ?: "통신 오류")
            }

        })
    }

    fun tryPutMemo(roomId: Int, memoId: Int, request: PutMemoRequest){
        val retrofit = ApplicationClass.sRetrofit.create(MemoInterface::class.java)
        retrofit.putMemo(roomId, memoId, request).enqueue(object: Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                view.onPutMemoSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onPutMemoFailure(t.message ?:"통신 오류")
            }

        })
    }

}