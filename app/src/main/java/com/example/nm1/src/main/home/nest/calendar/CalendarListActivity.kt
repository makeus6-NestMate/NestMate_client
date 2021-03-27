package com.example.nm1.src.main.home.nest.calendar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.nm1.R
import com.example.nm1.config.ApplicationClass
import com.example.nm1.config.BaseActivity
import com.example.nm1.databinding.*
import com.example.nm1.src.main.home.nest.calendar.model.*
import kotlinx.android.synthetic.main.calendar_list_item.view.*
import kotlinx.android.synthetic.main.dialog_calendar_list_bottom.*
import java.text.SimpleDateFormat
import java.util.*

class CalendarListActivity : BaseActivity<ActivityCalendarListBinding>(ActivityCalendarListBinding::inflate), CalendarActivityView {

    private lateinit var selectedYear : String
    private lateinit var selectedMonth : String
    private lateinit var selectedDay : String

    private lateinit var toolbarTitle :String
    private lateinit var smallTitle :String
    private lateinit var calTitle :String
    private lateinit var date : String
    private var calCount :String = ""

    private lateinit var calList : List<CalendarDetailInfo>

    private val roomId = ApplicationClass.sSharedPreferences.getInt("roomId", 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        selectedYear=intent.getStringExtra("year").toString()
        selectedMonth=intent.getStringExtra("month").toString()
        selectedDay=intent.getStringExtra("day").toString()

        date = "$selectedYear/$selectedMonth/$selectedDay"
        showLoadingDialog(this)
        CalendarService(this).tryGetDetailCalendar(roomId, date)

        val currentTime = Calendar.getInstance().time
        val currentMonth: String = SimpleDateFormat("MM", Locale.KOREAN).format(currentTime)
        var currentDay: String = SimpleDateFormat("dd", Locale.KOREAN).format(currentTime)

        //대제목
        toolbarTitle = selectedYear+"년 "+selectedMonth+"월 "+selectedDay+"일"
        //소제목
        smallTitle = "개의 '"
        if(selectedMonth.toInt()>currentMonth.toInt()) calTitle="예정된"
        else if(selectedMonth.toInt()<currentMonth.toInt()) calTitle="지난"
        else{
            if(selectedDay.toInt()>currentDay.toInt()) calTitle="예정된"
            else if(selectedDay.toInt()<currentDay.toInt()) calTitle="지난"
            else calTitle="오늘"
        }
        smallTitle+=calTitle+"' 일정";

        binding.calendarListToolbar.toolbarTitle.text=toolbarTitle
        binding.calendarListToolbar.plusBtn.setOnClickListener {
            val intent = Intent(this, CalendarAddActivity::class.java)
            intent.putExtra("year", selectedYear)
            intent.putExtra("month", selectedMonth)
            intent.putExtra("day", selectedDay)
            startActivity(intent)
        }
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
        CalendarService(this).tryGetDetailCalendar(roomId, date)
    }

    override fun onDeleteCalendarFailure(message: String) {
        showCustomToast("다시 시도 해주세요.")
    }

    override fun onGetCalendarSuccess(response: GetCalendarResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetCalendarFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetDetailCalendarSuccess(response: GetDetailCalendarResponse) {
        calList = response.result.calendarDetailInfo
        val calListAdapter = CalendarListAdapter(this, calList)
        calCount = calListAdapter.itemCount.toString()
        binding.calendarListTitle.text=calCount+smallTitle
        calListAdapter.itemSetClick = object: CalendarListAdapter.ItemSetClick {
            override fun onClick(view: View, position: Int) {
                val bottomDialogFragment = CalendarListBottomDialog{
                    when(it){
                        0 -> {
                            val intent = Intent(view.context, CalendarAddActivity::class.java)
                            intent.putExtra("position",position)
                            intent.putExtra("cateIdx", calList[position].categoryIdx)
                            intent.putExtra("cateName", calList[position].category)
                            intent.putExtra("title", calList[position].title)
                            intent.putExtra("datetime", calList[position].time)
                            intent.putExtra("memo", calList[position].content)
                            startActivity(intent)
                        }
                        1 -> {
                            CalendarService(this@CalendarListActivity).tryDeleteCalendar(roomId, calList[position].calendarId)
                        }
                    }
                }
                bottomDialogFragment.show(supportFragmentManager, bottomDialogFragment.tag)
            }
        }
        calListAdapter.itemClick = object: CalendarListAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(view.context, CalendarListDetailActivity::class.java)
                intent.putExtra("calendarId",calList[position].calendarId)
                intent.putExtra("toolbar", toolbarTitle)
                intent.putExtra("cateIdx", calList[position].categoryIdx)
                intent.putExtra("cateName", calList[position].category)
                intent.putExtra("title", calList[position].title)
                intent.putExtra("datetime", calList[position].time)
                intent.putExtra("memo", calList[position].content)
                startActivity(intent)
            }
        }
        binding.calendarScheduleList.adapter = calListAdapter
        calListAdapter.notifyDataSetChanged()

        dismissLoadingDialog()
    }

    override fun onGetDetailCalendarFailure(message: String) {
        TODO("Not yet implemented")
    }
}