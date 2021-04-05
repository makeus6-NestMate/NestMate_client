package com.nestmate.nm1.src.main.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.nestmate.nm1.R
import com.nestmate.nm1.config.ApplicationClass
import com.nestmate.nm1.config.BaseFragment
import com.nestmate.nm1.databinding.MyFragmentBinding
import com.nestmate.nm1.src.login.LoginActivity
import com.nestmate.nm1.src.main.mypage.model.GetProfileResponse
import com.nestmate.nm1.src.main.mypage.profile.ProfileActivity
import kotlinx.android.synthetic.main.toolbar_back.*

class MyFragment : BaseFragment<MyFragmentBinding>(MyFragmentBinding::bind, R.layout.my_fragment), MyPageView{
    private var profileImg: String? = null
    private var nickname: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.myToolbar.toolbarTitle.text="마이페이지"

        binding.mypageLogout.setOnClickListener {
            showLoadingDialog(requireContext())
            val editor = ApplicationClass.sSharedPreferences.edit()
            editor.putString(ApplicationClass.X_ACCESS_TOKEN, "na")
            editor.apply()

            dismissLoadingDialog()
            val intent = Intent(activity, LoginActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            activity?.finish()
        }

        binding.mypageLeave.setOnClickListener {
            val dialog = MyPageDialog()
            dialog.show(childFragmentManager, dialog.tag)
        }

        binding.mypageQuestion.setOnClickListener {
            val dialog = MyPageDialog()
            dialog.show(childFragmentManager, dialog.tag)
        }

        binding.mypageProfile.setOnClickListener {
            val intent = Intent(activity, ProfileActivity::class.java)
            intent.putExtra("profileImg", profileImg)
            intent.putExtra("nickname", nickname)
            startActivity(intent)

        }


    }

    override fun onResume() {
        super.onResume()

        MyPageService(this).tryGetProfile()
        showLoadingDialog(requireContext())
    }

    override fun onGetProfileSuccess(response: GetProfileResponse) {
        dismissLoadingDialog()
        when(response.code){
            200 -> {
                Glide.with(this).load(response.result.user.profileImg).error(R.drawable.chicken_img).into(binding.mypageProfileImg)
                binding.mypageNickname.text = response.result.user.nickname
                binding.mypageBestmateCount.text = (response.result.user.prizeCount.toString()) + "회"

                profileImg = response.result.user.profileImg
                nickname = response.result.user.nickname
            }
            else -> {
                showCustomToast(response.message.toString())
            }
        }
    }

    override fun onGetProfileFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }
}