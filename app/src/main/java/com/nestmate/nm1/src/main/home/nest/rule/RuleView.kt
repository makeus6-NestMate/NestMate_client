package com.nestmate.nm1.src.main.home.nest.rule

import com.nestmate.nm1.config.BaseResponse
import com.nestmate.nm1.src.main.home.nest.rule.model.GetRuleResponse

interface RuleView {
    fun onPostRuleSuccess(response: BaseResponse)
    fun onPostRuleFailure(message: String)

    fun onGetRuleSuccess(response: GetRuleResponse)
    fun onGetRuleFailure(message: String)

    fun onDeleteRuleSuccess(response: BaseResponse)
    fun onDeleteRuleFailure(message: String)

    fun onPutRuleSuccess(response: BaseResponse)
    fun onPutRuleFailure(message: String)
}