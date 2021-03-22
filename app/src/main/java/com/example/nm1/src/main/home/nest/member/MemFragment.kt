package com.example.nm1.src.main.home.nest.member

import android.os.Bundle
import android.view.View
import com.example.nm1.R
import com.example.nm1.config.BaseFragment
import com.example.nm1.databinding.FragmentMemBinding

class MemFragment : BaseFragment<FragmentMemBinding>(FragmentMemBinding::bind, R.layout.fragment_mem) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//       멤버추가
        binding.memLayoutPlus.setOnClickListener {
            val memberPlusBottomSheet = MemberPlusBottomSheet()
            memberPlusBottomSheet.show(parentFragmentManager, memberPlusBottomSheet.tag)
        }
    }
}