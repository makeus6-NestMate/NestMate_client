package com.example.nm1.src.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.nm1.config.ApplicationClass
import com.example.nm1.config.BaseActivity
import com.example.nm1.databinding.ActivityLoginBinding
import com.example.nm1.src.login.model.KakaoLoginResponse
import com.example.nm1.src.login.model.KakaoRegisterResponse
import com.example.nm1.src.login.model.PostKakaoLoginRequest
import com.example.nm1.src.main.MainActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate), LoginActivityView {
    var profileImg:String? = null
    var email:String?=null
    var access_token:String?=null
    val editor = ApplicationClass.sSharedPreferences.edit()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 로그인 공통 callback 구성
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("kakaologin", "로그인 실패", error)
            } else if (token != null) {
                Log.i("kakaologin", "로그인 성공 ${token.accessToken}")
                access_token = token.accessToken

                UserApiClient.instance.me { user, error ->
                    if (error != null) {
                        Log.e("kakaologin", "사용자 정보 요청 실패", error)
                    } else if (user != null) {

                        email = user.kakaoAccount?.email!!
                        profileImg = user.kakaoAccount?.profile?.profileImageUrl

                        if (email!=null && profileImg!=null) {
                            editor.putString(ApplicationClass.kakaoToken, access_token)
                            editor.apply()

                            //  카카오 회원가입
                            if (!ApplicationClass.sSharedPreferences.getBoolean(
                                    "iskakaoregisterd",
                                    false
                                )
                            ) {
                                val intent = Intent(this, KakaoRegisterActivity::class.java)
                                intent.putExtra("profileImg", profileImg)
                                intent.putExtra("email", email)

                                startActivity(intent)
                            } else { //카카오 로그인
                                this.finish()
                                showLoadingDialog(this)
                                val postKakaoRegisterRequest = PostKakaoLoginRequest(email!!,
                                    access_token!!
                                )
                                LoginService(this).tryPostKakaoLogin(postKakaoLoginRequest = postKakaoRegisterRequest)
                            }
                        }

                        Log.i(
                            "kakaologin", "사용자 정보 요청 성공" +
                                    "\n회원번호: ${user.id}" +
                                    "\n이메일: ${user.kakaoAccount?.email}" +
                                    "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                    "\n프로필사진: ${user.kakaoAccount?.profile?.profileImageUrl}"
                        )
                    }
                }
            }
        }

        binding.loginBtnKakao.setOnClickListener {
            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }
    }

    override fun onPostKakaoRegisterSuccess(response: KakaoRegisterResponse) {
        TODO("Not yet implemented")
    }

    override fun onPostKakaoRegisterFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onPostKakaoLoginSuccess(response: KakaoLoginResponse) {
        dismissLoadingDialog()
        editor.putString(ApplicationClass.X_ACCESS_TOKEN, response.result.token)
        editor.remove(ApplicationClass.kakaoToken)
        editor.apply()

        this.finish()
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onPostKakaoLoginFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }
}