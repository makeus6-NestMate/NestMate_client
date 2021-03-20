package com.example.nm1.src.main.home.nest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.nm1.R
import com.example.nm1.config.BaseActivity
import com.example.nm1.config.BaseFragment
import com.example.nm1.databinding.*
import kotlinx.android.synthetic.main.calendar_list_item.view.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class CalendarListActivity : BaseActivity<ActivityCalendarListBinding>(ActivityCalendarListBinding::inflate) {
    var calList = arrayListOf<CalendarList>(CalendarList("aaaa", "bbbb","cccc"))

    private lateinit var selectedYear : String
    private lateinit var selectedMonth : String
    private lateinit var selectedDay : String

    private lateinit var toolbarTitle :String
    private lateinit var calTitle :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        selectedYear=intent.getStringExtra("year").toString()
        selectedMonth=intent.getStringExtra("month").toString()
        selectedDay=intent.getStringExtra("day").toString()
        val currentTime = Calendar.getInstance().time
        val currentMonth: String = SimpleDateFormat("MM", Locale.KOREAN).format(currentTime)
        val currentDay: String = SimpleDateFormat("dd", Locale.KOREAN).format(currentTime)

        toolbarTitle = selectedYear+"년 "+selectedMonth+"월 "+selectedDay+"일"

        if(selectedMonth.toInt()>currentMonth.toInt()) calTitle="예정된"
        else if(selectedMonth.toInt()<currentMonth.toInt()) calTitle="지난"
        else{
            if(selectedDay.toInt()>currentDay.toInt()) calTitle="예정된"
            else if(selectedDay.toInt()<currentDay.toInt()) calTitle="지난"
            else calTitle="오늘"
        }

        binding.calendarListToolbar.toolbarTitle.text=toolbarTitle
        binding.calendarListTitle.text=calTitle
        binding.calendarListToolbar.plusBtn.setOnClickListener {
            startActivity(Intent(this, CalendarAddActivity::class.java))
        }
        binding.calendarScheduleList.adapter = CalendarListAdapter(this, calList)
    }
}