package com.example.nm1.src.main.home.nest.memo

interface MemoAddInterface {

    fun onConfirmBtnClicked(isEdit: Boolean, memoId: Int?, message: String, color: String?)
    fun onCancelBtnClicked()
}