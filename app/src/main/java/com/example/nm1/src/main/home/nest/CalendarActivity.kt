package com.example.nm1.src.main.home.nest

import android.content.Intent
import android.os.Bundle
import com.example.nm1.R
import com.example.nm1.config.BaseActivity
import com.example.nm1.databinding.CalendarActivityBinding
import kotlinx.android.synthetic.main.calendar_activity.*
import kotlinx.android.synthetic.main.toolbar_back.*
import kotlinx.android.synthetic.main.toolbar_back_plus.*
import kotlinx.android.synthetic.main.toolbar_back_plus.toolbar_title
import kotlinx.android.synthetic.main.toolbar_back_plus.view.*
import java.util.*

class CalendarActivity : BaseActivity<CalendarActivityBinding>(CalendarActivityBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calendar_activity)

        toolbar_title.text="캘린더"

        plus_btn.setOnClickListener {
            startActivity(Intent(this, CalendarAdd::class.java))
        }
        back_btn_two.setOnClickListener {
            finish()
        }

        supportFragmentManager.beginTransaction().add(R.id.calendarFrame, CalendarViewPagerFragment()).commit()
    }
}