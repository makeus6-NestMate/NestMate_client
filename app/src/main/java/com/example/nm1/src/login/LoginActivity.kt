package com.example.nm1.src.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.nm1.R
import com.example.nm1.config.ApplicationClass
import com.example.nm1.config.BaseActivity
import com.example.nm1.databinding.ActivityLoginBinding
import com.example.nm1.src.login.model.LoginResponse
import com.example.nm1.src.login.model.PostLoginRequest
import com.example.nm1.src.main.MainActivity
import com.example.nm1.src.register.RegisterOneActivity
import com.example.nm1.util.onMyTextChanged

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate), LoginActivityView {
    private var isEmail = false
    private var isPW = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.loginBtn.setOnClickListener {
            val request = PostLoginRequest(binding.loginEmail.text.toString(), binding.loginPw.text.toString())
            LoginService(this).tryPostLogin(request)
        }

        binding.loginRegister.setOnClickListener {
            val intent = Intent(this, RegisterOneActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        }

        binding.loginEmail.onMyTextChanged {
            isEmail = binding.loginEmail.text.toString().isNotEmpty()

            if(isEmail && isPW){
                binding.loginBtn.setBackgroundResource(R.drawable.roundrec_design_active_bg)
            }else{
                binding.loginBtn.setBackgroundResource(R.drawable.roundrec_design_inactive_bg)
            }
        }

        binding.loginPw.onMyTextChanged {
            isPW = binding.loginPw.text.toString().isNotEmpty()

            if(isEmail && isPW){
                binding.loginBtn.setBackgroundResource(R.drawable.roundrec_design_active_bg)
            }else{
                binding.loginBtn.setBackgroundResource(R.drawable.roundrec_design_inactive_bg)
            }
        }
    }

    override fun onPostLoginSuccess(response: LoginResponse) {
        when(response.code){
            200 -> {
                showCustomToast(response.message.toString())
                val token = response.result.token
                val editor = ApplicationClass.sSharedPreferences.edit()
                Log.d("로그", "Login Success!! ${token}")

                editor.putString(ApplicationClass.X_ACCESS_TOKEN, token)
                editor.apply()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else -> {
                showCustomToast(response.message.toString())
            }
        }
    }

    override fun onPostLoginFailure(message: String) {
        showCustomToast(message)
    }

}