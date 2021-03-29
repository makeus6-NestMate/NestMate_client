package com.example.nm1.src.main.home.nest.memo.model

import com.google.gson.annotations.SerializedName

data class MemoData(@SerializedName("memoId") val memoId: Int,
                    @SerializedName("profileImg") val profileImg: String,
                    @SerializedName("createdAt") val createdAt: String,
                    @SerializedName("memo") val memo: String,
                    @SerializedName("memoColor") val memoColor: String,
                    @SerializedName("x") val x:Float,
                    @SerializedName("y") val y: Float,
                    @SerializedName("isOwner") val isOwner: String
                    )
