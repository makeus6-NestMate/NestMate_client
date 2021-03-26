package com.example.nm1.src.main.home.nest

import android.content.Intent
import android.os.Bundle
import com.example.nm1.R
import com.example.nm1.config.ApplicationClass
import com.example.nm1.config.BaseActivity
import com.example.nm1.databinding.ActivityNestBinding

class NestActivity : BaseActivity<ActivityNestBinding>(ActivityNestBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.chart.setOnClickListener {
            startActivity(Intent(this, ChartActivity::class.java))
        }
        binding.calendar.setOnClickListener {
            startActivity(Intent(this, CalendarActivity::class.java))
        }

        // ViewPagerFragment를 fragmentFrame에 띄우기
        supportFragmentManager.beginTransaction().add(R.id.innerFrame, ViewPagerFragment()).commit()

        binding.toolbarTitle.text = ApplicationClass.sSharedPreferences.getString("roomName", null)
    }
}