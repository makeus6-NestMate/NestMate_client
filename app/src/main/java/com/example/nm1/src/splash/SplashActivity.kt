package com.example.nm1.src.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.nm1.config.BaseActivity
import com.example.nm1.databinding.ActivitySplashBinding
import com.example.nm1.src.main.MainActivity
import com.example.nm1.src.main.home.nest.NestActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, NestActivity::class.java))
            finish()
        }, 2000)
    }
}