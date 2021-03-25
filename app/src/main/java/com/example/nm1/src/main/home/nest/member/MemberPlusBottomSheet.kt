package com.example.nm1.src.main.home.nest.member

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nm1.config.ApplicationClass
import com.example.nm1.databinding.MemberBottomsheetPlusmemberBinding
import com.example.nm1.util.LoadingDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MemberPlusBottomSheet: BottomSheetDialogFragment(){
    private lateinit var mLoadingDialog: LoadingDialog
    private val roomId = ApplicationClass.sSharedPreferences.getInt("roomId", 0)
    private lateinit var binding: MemberBottomsheetPlusmemberBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = MemberBottomsheetPlusmemberBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.memberLayoutPlusEmail.setOnClickListener{
            val memberAddByEmailDialogFragment = MemberAddByEmailDialogFragment()
            memberAddByEmailDialogFragment.show(parentFragmentManager, memberAddByEmailDialogFragment.tag)
            this.dismiss()
        }
//
//        binding.memberLayoutPlusKakao.setOnClickListener{
//
//        }
    }

    private fun showLoadingDialog(context: Context) {
        mLoadingDialog = LoadingDialog(context)
        mLoadingDialog.show()
    }

    private fun dismissLoadingDialog() {
        if (mLoadingDialog.isShowing) {
            mLoadingDialog.dismiss()
        }
    }
}