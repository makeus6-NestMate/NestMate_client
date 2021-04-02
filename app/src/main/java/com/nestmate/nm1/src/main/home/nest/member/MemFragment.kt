package com.nestmate.nm1.src.main.home.nest.member

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import com.nestmate.nm1.R
import com.nestmate.nm1.config.ApplicationClass
import com.nestmate.nm1.config.BaseFragment
import com.nestmate.nm1.databinding.FragmentMemBinding
import com.nestmate.nm1.src.main.home.HomeService
import com.nestmate.nm1.src.main.home.nest.member.model.DeleteMeFromNestResponse
import com.nestmate.nm1.src.main.home.nest.member.model.GetMemberResponse
import com.nestmate.nm1.src.main.home.nest.member.model.ResponseAddMemberByEmail

class MemFragment : BaseFragment<FragmentMemBinding>(FragmentMemBinding::bind, R.layout.fragment_mem), MemberView {
    private val roomId = ApplicationClass.sSharedPreferences.getInt("roomId", 0)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//       멤버추가
        binding.memLayoutPlus.setOnClickListener {
            val memberPlusBottomSheet = MemberPlusBottomSheet()
            memberPlusBottomSheet.show(parentFragmentManager, memberPlusBottomSheet.tag)
        }

        //  멤버 추가 후 바로 반영
        setFragmentResultListener("addmember") { _, bundle ->
            bundle.getString("addmember_ok")?.let {
                if (it == "ok") {

                    showLoadingDialog(requireContext())
                    MemberService(this).tryGetMember(roomId)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        //       멤버 얻어오기
        showLoadingDialog(requireContext())
        MemberService(this).tryGetMember(roomId)
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