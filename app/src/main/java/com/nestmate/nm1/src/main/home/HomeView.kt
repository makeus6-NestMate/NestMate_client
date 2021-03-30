package com.nestmate.nm1.src.main.home

import com.nestmate.nm1.src.main.home.model.AddNestResponse
import com.nestmate.nm1.src.main.home.model.GetNestResponse
import com.nestmate.nm1.src.main.home.model.PutEditNestResponse

interface HomeView {
//   ?? ???
    fun onAddNestSuccess(response: AddNestResponse)
    fun onAddNestFailure(message: String)

//    ?? ????
    fun onGetNestSuccess(response: GetNestResponse)
    fun onGetNestFailure(message: String)

//    둥지 수정
    fun onPutNestSuccess(response: PutEditNestResponse)
    fun onPutNestFailure(message: String)
}