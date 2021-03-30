package com.nestmate.nm1.src.main.home.nest.rule

import com.nestmate.nm1.config.ApplicationClass
import com.nestmate.nm1.config.BaseResponse
import com.nestmate.nm1.src.main.home.nest.rule.model.GetRuleResponse
import com.nestmate.nm1.src.main.home.nest.rule.model.PostRuleRequest
import com.nestmate.nm1.src.main.home.nest.rule.model.PutRuleRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RuleService(val view: RuleView) {

    fun tryPostRule(roomId: Int, request: PostRuleRequest){
        val retrofit = ApplicationClass.sRetrofit.create(RuleInterface::class.java)
        retrofit.postRule(roomId, request).enqueue(object: Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                view.onPostRuleSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onPostRuleFailure(t.message ?: "통신 오류")
            }

        })
    }

    fun tryGetRule(roomId: Int){
        val retrofit = ApplicationClass.sRetrofit.create(RuleInterface::class.java)
        retrofit.getRule(roomId).enqueue(object: Callback<GetRuleResponse>{
            override fun onResponse(
                call: Call<GetRuleResponse>,
                response: Response<GetRuleResponse>
            ) {
                view.onGetRuleSuccess(response.body() as GetRuleResponse)
            }

            override fun onFailure(call: Call<GetRuleResponse>, t: Throwable) {
                view.onGetRuleFailure(t.message ?: "통신 오류")
            }

        })
    }

    fun tryDeleteRule(roomId: Int, ruleId: Int){
        val retrofit = ApplicationClass.sRetrofit.create(RuleInterface::class.java)
        retrofit.deleteRule(roomId, ruleId).enqueue(object: Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                view.onDeleteRuleSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onDeleteRuleFailure(t.message ?: "통신 오류")
            }

        })
    }

    fun tryPutRule(roomId: Int, ruleId: Int, request: PutRuleRequest){
        val retrofit = ApplicationClass.sRetrofit.create(RuleInterface::class.java)
        retrofit.putRule(roomId, ruleId, request).enqueue(object: Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                view.onPutRuleSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onPutRuleFailure(t.message ?: "통신 오류")
            }

        })
    }
}