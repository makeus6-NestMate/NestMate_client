package com.example.nm1.src.main.home.nest.notice

import com.example.nm1.config.ApplicationClass
import com.example.nm1.config.BaseResponse
import com.example.nm1.src.main.home.nest.notice.model.GetNoticeVoteResponse
import com.example.nm1.src.main.home.nest.notice.model.PostNoticeRequest
import com.example.nm1.src.main.home.nest.notice.model.PostVoteRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoticeVoteService(val view: NoticeVoteView) {

    fun tryPostNotice(roomId: Int, request: PostNoticeRequest){
        val retrofit = ApplicationClass.sRetrofit.create(NoticeVoteInterface::class.java)
        retrofit.postNotice(roomId, request).enqueue(object: Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                view.onPostNoticeSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onPostNoticeFailure(t.message ?: "통신 오류")
            }

        })
    }

    fun tryGetNoticeVote(roomId: Int, page: Int){
        val retrofit = ApplicationClass.sRetrofit.create(NoticeVoteInterface::class.java)
        retrofit.getNoticeVote(roomId, page).enqueue(object: Callback<GetNoticeVoteResponse>{
            override fun onResponse(
                call: Call<GetNoticeVoteResponse>,
                response: Response<GetNoticeVoteResponse>
            ) {
                view.onGetNoticeVoteSuccess(response.body() as GetNoticeVoteResponse)
            }

            override fun onFailure(call: Call<GetNoticeVoteResponse>, t: Throwable) {
                view.onGetNoticeVoteFailure(t.message ?: "통신 오류")
            }

        })
    }

    fun tryPostVote(roomId: Int, request: PostVoteRequest){
        val retrofit = ApplicationClass.sRetrofit.create(NoticeVoteInterface::class.java)
        retrofit.postVote(roomId, request).enqueue(object: Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                view.onPostVoteSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onPostVoteFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryDeleteNotice(roomId: Int, noticeId: Int){
        val retrofit = ApplicationClass.sRetrofit.create(NoticeVoteInterface::class.java)
        retrofit.deleteNotice(roomId, noticeId).enqueue(object: Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                view.onDeleteNoticeSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onDeleteNoticeFailure(t.message ?: "통신 오류")
            }

        })
    }

    fun tryDeleteVote(roomId: Int, voteId: Int){
        val retrofit = ApplicationClass.sRetrofit.create(NoticeVoteInterface::class.java)
        retrofit.deleteVote(roomId, voteId).enqueue(object: Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                view.onDeleteVoteSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onDeleteVoteFailure(t.message ?: "통신 오류")
            }

        })
    }
}