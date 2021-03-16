package com.example.nm1.src.main.home

import com.example.nm1.src.main.home.model.AddNestResponse

interface HomeFragmentView {
//   ?? ???
    fun onAddNestSuccess(response: AddNestResponse)
    fun onAddNestFailure(message: String)
}