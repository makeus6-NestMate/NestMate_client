package com.nestmate.nm1.src.main.home.nest.chart.model

import com.google.gson.annotations.SerializedName

data class ResultGetChart (@SerializedName("chart") val chart:List<List<ChartDataInfo>>,
                           @SerializedName("bestMember") val bestMember:ChartBestInfo,
                           @SerializedName("member") val member:List<ChartMemberInfo>)