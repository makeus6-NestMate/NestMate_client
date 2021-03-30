package com.nestmate.nm1.src.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.nestmate.nm1.config.ApplicationClass
import com.nestmate.nm1.config.BaseActivity
import com.nestmate.nm1.config.BaseResponse
import com.nestmate.nm1.databinding.ActivityLoginBinding
import com.nestmate.nm1.src.login.model.KakaoLoginResponse
import com.nestmate.nm1.src.login.model.PostKakaoLoginRequest
import com.nestmate.nm1.src.main.MainActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate), LoginActivityView {
    private var kakaoImg:String? = null
    var email:String?=null
    private var access_token:String?=null
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
                        kakaoImg = user.kakaoAccount?.profile?.profileImageUrl

                        if (email!=null && kakaoImg!=null) {

                            //  먼저 로그인 진입후 -> 회원이 아니면 회원가입을 시킴
                            showLoadingDialog(this)
                            val postKakaoLoginRequest = PostKakaoLoginRequest(email!!,
                                access_token!!
                            )
                            LoginService(this).tryPostKakaoLogin(postKakaoLoginRequest = postKakaoLoginRequest)
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
            showCustomToast("회원가입 시 전체 동의하기를 눌러주세요 (카카오 계정 이메일 포함)")
            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }
    }

    override fun onPostKakaoRegisterSuccess(response: BaseResponse) {
        TODO("Not yet implemented")
    }

    override fun onPostKakaoRegisterFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onPostKakaoLoginSuccess(response: KakaoLoginResponse) {
        dismissLoadingDialog()
        if (response.code==423){ //회원이 아님 -> 회원가입으로 이동
            val intent = Intent(this, KakaoRegisterActivity::class.java)
            intent.putExtra("email", email)
            intent.putExtra("access_token", access_token)
            intent.putExtra("kakaoImg", kakaoImg)

            startActivity(intent)
        }
        else if (response.code==200){ //회원임 -> 토큰값을 저장하면서 로그인
            editor.putString(ApplicationClass.X_ACCESS_TOKEN, response.result.token)
            editor.apply()

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            this.finish()
        }
    }

    override fun onPostKakaoLoginFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }
}