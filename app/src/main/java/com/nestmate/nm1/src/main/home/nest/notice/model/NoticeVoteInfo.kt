package com.nestmate.nm1.src.main.home.nest.notice.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NoticeVoteInfo(@SerializedName("profileImg") val profileImg: String,
                          @SerializedName("noticeId") val noticeId: Int? = null,
                          @SerializedName("notice") var notice: String? = null,
                          @SerializedName("createdAt") val createdAt: String,
                          @SerializedName("isNotice") val isNotice: String,
                          @SerializedName("voteId") val voteId: Int? = null,
                          @SerializedName("title") val title: String? = null,
                          @SerializedName("isFinished") val isFinished: String? = null,
                          @SerializedName("isOwner") val isOwner: String
                          ): Serializable
