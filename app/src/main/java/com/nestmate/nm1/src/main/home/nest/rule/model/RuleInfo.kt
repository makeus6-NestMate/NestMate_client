package com.nestmate.nm1.src.main.home.nest.rule.model

import com.google.gson.annotations.SerializedName

data class RuleInfo(@SerializedName("rule") val rule: String, @SerializedName("ruleId") val ruleId: Int,
                    @SerializedName("isOwner") val isOwner: String)
