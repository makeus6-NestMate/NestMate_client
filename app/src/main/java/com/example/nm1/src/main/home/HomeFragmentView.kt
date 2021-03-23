package com.example.nm1.src.main.home

import com.example.nm1.src.main.home.model.AddNestResponse
import com.example.nm1.src.main.home.model.GetNestResponse

interface HomeFragmentView {
//   ?? ???
    fun onAddNestSuccess(response: AddNestResponse)
    fun onAddNestFailure(message: String)

//    ?? ????
    fun onGetNestSuccess(response: GetNestResponse)
    fun onGetNestFailure(message: String)
}