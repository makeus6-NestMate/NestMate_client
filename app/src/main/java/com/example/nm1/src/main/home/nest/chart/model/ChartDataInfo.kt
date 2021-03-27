package com.example.nm1.src.main.home.nest.chart.model

import androidx.annotation.Nullable
import com.google.gson.annotations.SerializedName

data class ChartDataInfo (
    @SerializedName("userId") val userId: Int,
    @SerializedName("count") val count : Int)