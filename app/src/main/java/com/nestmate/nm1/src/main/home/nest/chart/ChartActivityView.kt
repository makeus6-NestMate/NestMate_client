package com.nestmate.nm1.src.main.home.nest.chart

import com.nestmate.nm1.src.main.home.nest.chart.model.GetChartResponse
import com.nestmate.nm1.src.main.home.nest.chart.model.PostChartClapResponse

interface ChartActivityView {
    fun onPostChartClapSuccess(response: PostChartClapResponse)
    fun onPostChartClapFailure(message: String)

    fun onGetChartSuccess(response: GetChartResponse)
    fun onGetChartFailure(message: String)
}