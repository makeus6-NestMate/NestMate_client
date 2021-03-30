package com.nestmate.nm1.src.main.home.nest.memo

import com.nestmate.nm1.config.BaseResponse
import com.nestmate.nm1.src.main.home.nest.memo.model.GetMemoResponse

interface MemoView {
    fun onPostMemoSuccess(response: BaseResponse)
    fun onPostMemoFailure(message: String)

    fun onGetMemoSuccess(response: GetMemoResponse)
    fun onGetMemoFailure(message: String)

    fun onDeleteMemoSuccess(response: BaseResponse)
    fun onDeleteMemoFailure(message: String)

    fun onPatchMemoSuccess(response: BaseResponse)
    fun onPatchMemoFailure(message: String)

    fun onPutMemoSuccess(response: BaseResponse)
    fun onPutMemoFailure(message: String)
}