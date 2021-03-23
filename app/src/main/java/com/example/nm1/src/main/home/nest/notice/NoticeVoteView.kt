package com.example.nm1.src.main.home.nest.notice

import com.example.nm1.config.BaseResponse
import com.example.nm1.src.main.home.nest.notice.model.GetNoticeVoteResponse

interface NoticeVoteView {
    fun onPostNoticeSuccess(response: BaseResponse)
    fun onPostNoticeFailure(message: String)

    fun onGetNoticeVoteSuccess(response: GetNoticeVoteResponse)
    fun onGetNoticeVoteFailure(message: String)

    fun onPostVoteSuccess(response: BaseResponse)
    fun onPostVoteFailure(message: String)

    fun onDeleteNoticeSuccess(response: BaseResponse)
    fun onDeleteNoticeFailure(message: String)

    fun onDeleteVoteSuccess(response: BaseResponse)
    fun onDeleteVoteFailure(message: String)
}