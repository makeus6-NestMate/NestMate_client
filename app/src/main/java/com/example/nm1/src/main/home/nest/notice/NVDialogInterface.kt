package com.example.nm1.src.main.home.nest.notice

interface NVDialogInterface {
    fun onEditClicked()
    fun onDeleteClicked(isNotice: Boolean, position: Int, noticeId: Int? = null, voteId: Int? = null)
}