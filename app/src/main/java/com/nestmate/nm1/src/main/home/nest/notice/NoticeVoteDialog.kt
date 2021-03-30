package com.nestmate.nm1.src.main.home.nest.notice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nestmate.nm1.databinding.DialogNvMoreBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NoticeVoteDialog(isNotice: Boolean, position: Int, noticeId: Int? = null, voteId: Int? = null, nvDialogInterface: NVDialogInterface): BottomSheetDialogFragment() {
    private lateinit var binding: DialogNvMoreBinding
    private var isNotice = isNotice
    private var position = position
    private var noticeId = noticeId
    private var voteId = voteId
    private var nvDialogInterface = nvDialogInterface

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogNvMoreBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.dialogNvDeleteBtn.setOnClickListener {
            if(isNotice){
                nvDialogInterface.onDeleteClicked(isNotice = true, position=position, noticeId = noticeId)
                dismiss()
            }else{
                nvDialogInterface.onDeleteClicked(isNotice = false, position=position, voteId = voteId)
                dismiss()
            }
        }

        binding.dialogNvEditBtn.setOnClickListener {
            nvDialogInterface.onEditClicked(isNotice = true, isEdit = true, position = position, noticeId = noticeId!!)
            dismiss()
        }
    }
}