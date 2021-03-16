package com.example.nm1.src.splash

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import com.example.nm1.config.ApplicationClass
import com.example.nm1.config.BaseActivity
import com.example.nm1.databinding.ActivitySplashBinding
import com.example.nm1.src.login.LoginActivity
import com.example.nm1.src.main.MainActivity
import com.example.nm1.src.main.home.nest.NestActivity
import com.example.nm1.src.main.mypage.MyFragment
import com.example.nm1.src.register.RegisterOneActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//       ??? x -> ?? x
        if(ApplicationClass.sSharedPreferences.getString("JWT", "na") == "na"){
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }, 2000)
        }else{
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, NestActivity::class.java))
                finish()
            }, 2000)
        }

        //       ???? ?????
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }
        else {
            @Suppress("DEPRECATION")
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }
}