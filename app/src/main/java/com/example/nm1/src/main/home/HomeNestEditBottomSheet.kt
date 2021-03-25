package com.example.nm1.src.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nm1.config.ApplicationClass
import com.example.nm1.databinding.HomeNestBottomsheetEditBinding
import com.example.nm1.util.LoadingDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class HomeNestEditBottomSheet: BottomSheetDialogFragment(){
    private lateinit var mLoadingDialog: LoadingDialog
    private val roomId = ApplicationClass.sSharedPreferences.getInt("roomId", 0)
    private lateinit var binding: HomeNestBottomsheetEditBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = HomeNestBottomsheetEditBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.homeNestLayoutEdit.setOnClickListener {
            val nestEditDialog = NestEditDialog()
            nestEditDialog.show(parentFragmentManager, nestEditDialog.tag)
            this.dismiss()
        }
    }
}