package com.example.nm1.src.main.home.nest.notice

import java.io.Serializable

data class NoticeVoteData(val isNotice: Boolean, val content : String? = null, val title: String? = null, val choiceArr: ArrayList<String>? = null): Serializable