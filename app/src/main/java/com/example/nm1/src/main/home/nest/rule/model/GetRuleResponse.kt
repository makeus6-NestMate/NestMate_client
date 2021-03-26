package com.example.nm1.src.main.home.nest.rule.model

import com.example.nm1.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class GetRuleResponse(@SerializedName("result") val result: ResultRule): BaseResponse()
