package com.example.nm1.src.main.home.nest.rule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nm1.databinding.MemoDeleteDialogBinding
import com.example.nm1.databinding.RuleMoreDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RuleDialog(position: Int, ruleId: Int, ruleDialogInterface: RuleDialogInterface): BottomSheetDialogFragment() {
    private lateinit var binding: RuleMoreDialogBinding
    private var ruleId = ruleId
    private var position = position
    private var ruleDialogInterface = ruleDialogInterface

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RuleMoreDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ruleDeleteBtn.setOnClickListener {
            ruleDialogInterface.onDeleteClicked(position, ruleId)
            dismiss()
        }

        binding.ruleEditBtn.setOnClickListener {
            ruleDialogInterface.onEditClicked(position, ruleId)
            dismiss()
        }
        dialog?.setCancelable(false)
    }
}