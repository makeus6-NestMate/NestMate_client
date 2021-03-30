package com.nestmate.nm1.src.main.home.nest.memo.model

import com.nestmate.nm1.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class GetMemoResponse(@SerializedName("result") val result: ResultMemo):BaseResponse()
