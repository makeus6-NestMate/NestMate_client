package com.example.nm1.src.main.home.nest.notice

import java.io.Serializable

data class NoticeVoteData(val isNotice: Boolean, val content : String, val timestamp: String, val ownerImage: String? = null): Serializable