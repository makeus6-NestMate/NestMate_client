package com.nestmate.nm1.src.main.home.nest.memo.model

import com.google.gson.annotations.SerializedName

data class ResultMemo(@SerializedName("memo") val memo: ArrayList<MemoData>)
