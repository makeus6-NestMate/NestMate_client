package com.example.nm1.src.login

import android.content.Intent
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.nm1.R
import com.example.nm1.config.ApplicationClass
import com.example.nm1.config.BaseActivity
import com.example.nm1.databinding.ActivityKakaoRegisterBinding
import com.example.nm1.src.login.model.KakaoLoginResponse
import com.example.nm1.src.login.model.KakaoRegisterResponse
import com.example.nm1.src.login.model.PostKakaoRegisterRequest
import com.example.nm1.src.main.MainActivity
import com.example.nm1.util.onMyTextChanged

class KakaoRegisterActivity : BaseActivity<ActivityKakaoRegisterBinding>(ActivityKakaoRegisterBinding::inflate),
    LoginActivityView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Glide.with(this)
            .load(intent.getStringExtra("profileImg"))
            .into(binding.kakaoregiProfile)

        binding.toolbarSetprofile.toolbarTitle.text = "프로필 설정"

        binding.kakaoregiEdtNickname.onMyTextChanged {
            if(binding.kakaoregiEdtNickname.text.length==2){
                binding.kakaoregiBtnConfirm.isEnabled = true
                binding.kakaoregiBtnConfirm.setBackgroundResource(R.drawable.roundrec_design_active_bg)
            }else{
                binding.kakaoregiBtnConfirm.isEnabled = false
                binding.kakaoregiBtnConfirm.setBackgroundResource(R.drawable.roundrec_design_inactive_bg)
            }
        }

        binding.kakaoregiBtnConfirm.setOnClickListener {
            val postKakaoRegisterRequest = PostKakaoRegisterRequest(binding.kakaoregiEdtNickname.text.toString(), intent.getStringExtra("profileImg"),
                intent.getStringExtra("email")!!)
            showLoadingDialog(this)
            LoginService(this).tryPostKakaoRegister(postKakaoRegisterRequest)
        }
    }


    override fun onPostKakaoRegisterSuccess(response: KakaoRegisterResponse) {
        dismissLoadingDialog()
        this.finish()

        ApplicationClass.sSharedPreferences.edit().putBoolean("iskakaoregisterd", true) //카카오 회원가입 성공
        ApplicationClass.sSharedPreferences.edit().apply()
    }

    override fun onPostKakaoRegisterFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onPostKakaoLoginSuccess(response: KakaoLoginResponse) {
        TODO("Not yet implemented")
    }

    override fun onPostKakaoLoginFailure(message: String) {
        TODO("Not yet implemented")
    }
}