package com.example.nm1.src.main.home.nest.calendar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nm1.R
import com.example.nm1.config.ApplicationClass
import com.example.nm1.config.BaseFragment
import com.example.nm1.databinding.FragmentCalendarBinding
import com.example.nm1.src.main.home.HomeService
import com.example.nm1.src.main.home.nest.calendar.model.*
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment : BaseFragment<FragmentCalendarBinding> (
    FragmentCalendarBinding::bind,
    R.layout.fragment_calendar), CalendarActivityView{
    var pageIndex = 0
    private lateinit var currentDate: Date
    private lateinit var datetime: String
    private val roomId = ApplicationClass.sSharedPreferences.getInt("roomId", 0)

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
        datetime = SimpleDateFormat("yyyy. MM", Locale.KOREA).format(date.time)
        binding.calendarYearMonthText.text=datetime

        binding.calendarView.layoutManager=GridLayoutManager(requireContext(),
            CalendarDay.DAYS_OF_WEEK
        )

        var dateArgu: String = SimpleDateFormat("yyyy/MM", Locale.KOREA).format(date.time)
        CalendarService(this).tryGetCalendar(roomId, dateArgu)
    }

    override fun onResume() {
        super.onResume()
        initView(0)
    }

    override fun onAddCalendarSuccess(response: AddCalendarResponse) {
        TODO("Not yet implemented")
    }

    override fun onAddCalendarFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onPutCalendarSuccess(response: PutCalendarResponse) {
        TODO("Not yet implemented")
    }

    override fun onPutCalendarFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteCalendarSuccess(response: DeleteCalendarResponse) {
        TODO("Not yet implemented")
    }

    override fun onDeleteCalendarFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetCalendarSuccess(response: GetCalendarResponse) {
        val calList = response.result.calendarInfo
        val calAdapter = CalendarAdapter(requireContext(), binding.calendarLayout, currentDate, calList)
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

    override fun onGetCalendarFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetDetailCalendarSuccess(response: GetDetailCalendarResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetDetailCalendarFailure(message: String) {
        TODO("Not yet implemented")
    }
}
