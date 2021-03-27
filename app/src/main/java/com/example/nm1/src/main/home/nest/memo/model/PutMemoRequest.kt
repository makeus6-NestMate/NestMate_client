package com.example.nm1.src.main.home.nest.memo.model

import com.google.gson.annotations.SerializedName

data class PutMemoRequest(@SerializedName("memo") val memo: String, @SerializedName("memoColor") val memoColor: String)
