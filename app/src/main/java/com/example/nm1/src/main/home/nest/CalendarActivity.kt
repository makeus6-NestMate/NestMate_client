package com.example.nm1.src.main.home.nest

import android.content.Intent
import android.os.Bundle
import com.example.nm1.R
import com.example.nm1.config.BaseActivity
import com.example.nm1.databinding.ActivityCalendarBinding

class CalendarActivity : BaseActivity<ActivityCalendarBinding>(ActivityCalendarBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.calendarToolbar.toolbarTitle.text="캘린더"

        binding.calendarToolbar.plusBtn.setOnClickListener {
            startActivity(Intent(this, CalendarAddActivity::class.java))
        }
        binding.calendarToolbar.backBtnTwo.setOnClickListener {
            finish()
        }

        supportFragmentManager.beginTransaction().add(R.id.calendarFrame, CalendarViewPagerFragment()).commit()
    }
}