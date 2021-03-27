package com.example.nm1.src.main.home.nest.member

import android.os.Bundle
import android.view.View
import com.example.nm1.R
import com.example.nm1.config.ApplicationClass
import com.example.nm1.config.BaseFragment
import com.example.nm1.databinding.FragmentMemBinding
import com.example.nm1.src.main.home.nest.member.model.DeleteMeFromNestResponse
import com.example.nm1.src.main.home.nest.member.model.GetMemberResponse
import com.example.nm1.src.main.home.nest.member.model.ResponseAddMemberByEmail

class MemFragment : BaseFragment<FragmentMemBinding>(FragmentMemBinding::bind, R.layout.fragment_mem), MemberView {
    private val roomId = ApplicationClass.sSharedPreferences.getInt("roomId", 0)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//       멤버 얻어오기
        showLoadingDialog(requireContext())
        MemberService(this).tryGetMember(roomId)

//       멤버추가
        binding.memLayoutPlus.setOnClickListener {
            val memberPlusBottomSheet = MemberPlusBottomSheet()
            memberPlusBottomSheet.show(parentFragmentManager, memberPlusBottomSheet.tag)
        }
    }

    override fun onAddMemberByEmailSuccess(response: ResponseAddMemberByEmail) {
        TODO("Not yet implemented")
    }

    override fun onAddMemberByEmailFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteMeFromNestSuccess(response: DeleteMeFromNestResponse) {
        TODO("Not yet implemented")
    }

    override fun onDeleteMeFromNestFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetMemberSuccess(response: GetMemberResponse) {
        dismissLoadingDialog()
        val adapter = MemberAdapter(requireContext(), response.result.member, parentFragmentManager)
        binding.memRecycler.adapter = adapter
    }

    override fun onGetMemberFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }
}
