package com.nestmate.nm1.src.main.home.nest.chart.model

import com.nestmate.nm1.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class GetChartResponse (@SerializedName("result") val result: ResultGetChart): BaseResponse()