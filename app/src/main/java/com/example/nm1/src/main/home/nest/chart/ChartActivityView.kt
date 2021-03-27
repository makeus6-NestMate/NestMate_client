package com.example.nm1.src.main.home.nest.chart

import com.example.nm1.src.main.home.nest.chart.model.GetChartResponse
import com.example.nm1.src.main.home.nest.chart.model.PostChartClapResponse
import com.example.nm1.src.main.home.nest.member.model.DeleteMeFromNestResponse
import com.example.nm1.src.main.home.nest.member.model.GetMemberResponse
import com.example.nm1.src.main.home.nest.member.model.ResponseAddMemberByEmail

interface ChartActivityView {
    fun onPostChartClapSuccess(response: PostChartClapResponse)
    fun onPostChartClapFailure(message: String)

    fun onGetChartSuccess(response: GetChartResponse)
    fun onGetChartFailure(message: String)
}