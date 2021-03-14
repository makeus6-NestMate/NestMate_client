package com.example.nm1.src.main.home.nest

import android.os.Bundle
import com.example.nm1.R
import com.example.nm1.config.BaseActivity
import com.example.nm1.databinding.ActivityNestBinding

class NestActivity : BaseActivity<ActivityNestBinding>(ActivityNestBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nest)

        // ViewPagerFragment를 fragmentFrame에 띄우기
        supportFragmentManager.beginTransaction().add(R.id.innerFrame, ViewPagerFragment()).commit()
    }
}