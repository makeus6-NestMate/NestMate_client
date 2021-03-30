package com.nestmate.nm1.src.main.home.nest.rule

interface RuleDialogInterface {
    fun onEditClicked(position: Int, ruleId: Int)
    fun onDeleteClicked(position: Int, ruleId: Int)
}