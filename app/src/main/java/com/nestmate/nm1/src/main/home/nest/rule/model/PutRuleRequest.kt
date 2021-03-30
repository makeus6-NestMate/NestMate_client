package com.nestmate.nm1.src.main.home.nest.rule.model

import com.google.gson.annotations.SerializedName

data class PutRuleRequest(@SerializedName("rule") val rule: String)
