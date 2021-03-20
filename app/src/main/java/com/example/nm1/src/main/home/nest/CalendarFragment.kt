package com.example.nm1.src.main.home.nest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nm1.R
import com.example.nm1.config.BaseFragment
import com.example.nm1.databinding.FragmentCalendarBinding
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment : BaseFragment<FragmentCalendarBinding> (
    FragmentCalendarBinding::bind,
    R.layout.fragment_calendar
){
    var pageIndex = 0
    private lateinit var currentDate: Date

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pageIndex-=(Int.MAX_VALUE / 2)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(0)
        binding.calendarBackBtn.setOnClickListener {
            initView(1)
        }
        binding.calendarFrontBtn.setOnClickListener {
            initView(2)
        }
    }

    fun initView(idx : Int) {
        if(idx==1) pageIndex--;
        else if(idx==2) pageIndex++;

        // 날짜 적용
        val date = Calendar.getInstance().run {
            add(Calendar.MONTH, pageIndex)
            time
        }
        currentDate = date
        // 포맷 적용
        var datetime: String = SimpleDateFormat("yyyy. MM", Locale.KOREA).format(date.time)
        binding.calendarYearMonthText.text=datetime

        binding.calendarView.layoutManager=GridLayoutManager(requireContext(), CalendarDay.DAYS_OF_WEEK)
        val calAdapter = CalendarAdapter(requireContext(), binding.calendarLayout, currentDate)
        calAdapter.itemClick = object: CalendarAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(requireContext(), CalendarListActivity::class.java)
                intent.putExtra("year", datetime.substring(0,4))
                intent.putExtra("month", datetime.substring(6,8).toInt().toString())
                intent.putExtra("day", calAdapter.dateList[position].toString())
                (activity as CalendarActivity).Change(intent)
            }
        }
        binding.calendarView.adapter=calAdapter
    }
}
