package com.example.nm1.src.main.home.nest.notice.model

import com.google.gson.annotations.SerializedName

data class PostVoteRequest(@SerializedName("title") val title: String, @SerializedName("choiceArr") val choiceArr: ArrayList<String>)
