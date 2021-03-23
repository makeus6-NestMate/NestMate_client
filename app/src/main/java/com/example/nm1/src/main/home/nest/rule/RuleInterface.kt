package com.example.nm1.src.main.home.nest.rule

import com.example.nm1.config.BaseResponse
import com.example.nm1.src.main.home.nest.rule.model.GetRuleResponse
import com.example.nm1.src.main.home.nest.rule.model.PostRuleRequest
import com.example.nm1.src.main.home.nest.rule.model.PutRuleRequest
import retrofit2.Call
import retrofit2.http.*

interface RuleInterface {
    @POST("/room/{roomId}/rule")
    fun postRule(@Path("roomId") roomId: Int, @Body params: PostRuleRequest): Call<BaseResponse>

    @GET("/room/{roomId}/rule")
    fun getRule(@Path("roomId") roomId: Int): Call<GetRuleResponse>

    @DELETE("/room/{roomId}/rule/{ruleId}")
    fun deleteRule(@Path("roomId") roomId: Int, @Path("ruleId") rule: Int): Call<BaseResponse>

    @PUT("/room/{roomId}/rule/{ruleId}")
    fun putRule(@Path("roomId") roomId: Int, @Path("ruleId") rule: Int, @Body params: PutRuleRequest): Call<BaseResponse>
}