package com.example.nm1.src.main.home.nest.calendar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private var sumList = mutableListOf<CalendarDetailInfo>()
    var calListAdapter:CalendarListAdapter?=null

    private var paging : Int = 0       // 현재 페이지
    var isEnded = false


    private val roomId = ApplicationClass.sSharedPreferences.getInt("roomId", 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        selectedYear=intent.getStringExtra("year").toString()
        selectedMonth=intent.getStringExtra("month").toString()
        selectedDay=intent.getStringExtra("day").toString()

        date = "$selectedYear/$selectedMonth/$selectedDay"


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

        binding.calendarScheduleList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

//             direction:  양수일경우엔 오른쪽 스크롤, 음수일경우엔 왼쪽 스크롤
//                수평으로 더이상 스크롤이 안되면, 데이터를 더해서 불러옴
                if (!binding.calendarScheduleList.canScrollVertically(1)){
                    if (!isEnded) {
                        showLoadingDialog(this@CalendarListActivity)
                        CalendarService(this@CalendarListActivity).tryGetDetailCalendar(roomId, date,++paging)
                    }
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        paging = 0
        sumList.clear()
        isEnded = false
        showLoadingDialog(this@CalendarListActivity)
        CalendarService(this@CalendarListActivity).tryGetDetailCalendar(roomId, date, paging)
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
        dismissLoadingDialog()
        onResume()
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
        dismissLoadingDialog()
        calList = response.result.calendarDetailInfo

        ///
        if (paging==0 && calList.isNotEmpty()){
            sumList.addAll(calList)
            calListAdapter = CalendarListAdapter(this, sumList)
            binding.calendarScheduleList.adapter = calListAdapter
        }
//      page=1부터 불러오고, 항목이 있으면
        else if (paging!=0 && calList.isNotEmpty()){
            sumList.addAll(calList)
            calListAdapter!!.notifyItemInserted(sumList.size-1)
        }
//        페이지추가 끝
        if (paging!=0 && calList.isNullOrEmpty()) isEnded=true
        ///

        calCount = response.result.calendarCnt.toString()
        binding.calendarListTitle.text=calCount+smallTitle
        calListAdapter?.itemSetClick = object: CalendarListAdapter.ItemSetClick {
            override fun onClick(view: View, position: Int) {
                val bottomDialogFragment = CalendarListBottomDialog{
                    when(it){
                        0 -> {
                            val intent = Intent(view.context, CalendarAddActivity::class.java)
                            intent.putExtra("calendarId",calList[position].calendarId)
                            intent.putExtra("position",position)
                            intent.putExtra("cateIdx", calList[position].categoryIdx)
                            intent.putExtra("cateName", calList[position].category)
                            intent.putExtra("title", calList[position].title)
                            intent.putExtra("datetime", calList[position].time)
                            intent.putExtra("memo", calList[position].content)
                            startActivity(intent)
                        }
                        1 -> {
                            showLoadingDialog(this@CalendarListActivity)
                            CalendarService(this@CalendarListActivity).tryDeleteCalendar(roomId, sumList[position].calendarId)
                        }
                    }
                }
                bottomDialogFragment.show(supportFragmentManager, bottomDialogFragment.tag)
            }
        }
        calListAdapter?.itemClick = object: CalendarListAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(view.context, CalendarListDetailActivity::class.java)
                intent.putExtra("calendarId",sumList[position].calendarId)
                intent.putExtra("toolbar", toolbarTitle)
                intent.putExtra("cateIdx", sumList[position].categoryIdx)
                intent.putExtra("cateName", sumList[position].category)
                intent.putExtra("title", sumList[position].title)
                intent.putExtra("datetime", sumList[position].time)
                intent.putExtra("memo", sumList[position].content)
                startActivity(intent)
            }
        }
        binding.calendarScheduleList.adapter = calListAdapter
    }

    override fun onGetDetailCalendarFailure(message: String) {
        showCustomToast("다시 시도 해주세요.")
    }
}