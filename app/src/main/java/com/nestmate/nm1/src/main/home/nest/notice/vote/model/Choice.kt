package com.nestmate.nm1.src.main.home.nest.notice.vote.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Choice(@SerializedName("choiceId") val choiceId: Int,
@SerializedName("choice") val choice: String,
@SerializedName("memberCnt") val memberCnt: Int): Serializable
