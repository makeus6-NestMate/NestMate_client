package com.example.nm1.src.main.home.nest.chart.model

import com.example.nm1.config.BaseResponse
import com.example.nm1.src.main.home.nest.todo.model.ResultGetOneDayTodo
import com.google.gson.annotations.SerializedName

data class GetChartResponse (@SerializedName("result") val result: ResultGetChart): BaseResponse()