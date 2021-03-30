package com.nestmate.nm1.src.main.home.nest.notice

interface NVDialogInterface {
    fun onEditClicked(isNotice: Boolean, isEdit: Boolean, position: Int, noticeId: Int)
    fun onDeleteClicked(isNotice: Boolean, position: Int, noticeId: Int? = null, voteId: Int? = null)
}