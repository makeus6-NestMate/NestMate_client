package com.example.nm1.src.main.home.nest.chart.model

import androidx.annotation.Nullable
import com.example.nm1.src.main.home.nest.todo.model.OneDayTodo
import com.google.gson.annotations.SerializedName

data class ResultGetChart (@SerializedName("chart") val chart:List<List<ChartDataInfo>>,
                           @SerializedName("bestMember") val bestMember:ChartBestInfo,
                           @SerializedName("member") val member:List<ChartMemberInfo>)