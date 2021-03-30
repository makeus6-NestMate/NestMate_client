package com.nestmate.nm1.src.main.home.nest.calendar

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import com.nestmate.nm1.config.ApplicationClass
import com.nestmate.nm1.config.BaseActivity
import com.nestmate.nm1.databinding.ActivityCalendarListDetailBinding
import com.nestmate.nm1.src.main.home.nest.calendar.model.*
import kotlinx.android.synthetic.main.activity_calendar_add.view.*
import kotlinx.android.synthetic.main.calendar_category.view.*

class CalendarListDetailActivity : BaseActivity<ActivityCalendarListDetailBinding>(
    ActivityCalendarListDetailBinding::inflate), CalendarActivityView {

    private val colorArray = arrayOf("#0b70c6", "#1bcbb0", "#95f288", "#fcd60a", "#f26317", "#b71bcb", "#94f5e6")
    private val nameArray = arrayOf("여행", "외식", "회의", "생일", "일반", "기타", "직접 입력")

    private val roomId = ApplicationClass.sSharedPreferences.getInt("roomId", 0)
    private var datetime = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val calendarId = intent.getIntExtra("calendarId", 0)
        binding.calendarListDetailToolbar.toolbarTitle.text=intent.getStringExtra("toolbar")
        val title = intent.getStringExtra("title")
        binding.calendarListDetailTitle.text=title
        datetime=intent.getStringExtra("datetime").toString()
        if(datetime!=null){
            var month=datetime.substring(5, 7); var day=datetime.substring(8, 10)
            var hour=datetime.substring(11, 13).toInt(); var min=datetime.substring(14, 16)
            var prefix = "오전 "
            if(hour>=12){//이게 맞나...?
                if(hour!=12) hour-=12
                prefix="오후 "
            }
            binding.calendarListDetailItemTimeTxt.text=month+"월 "+day+"일 "+prefix+hour.toString()+"시 "+min+"분"
        }
        val memo = intent.getStringExtra("memo")
        binding.calendarListDetailItemMemoTxt.text=memo
        //카테고리 관련
        val tmpIdx = intent.getIntExtra("cateIdx", 0)
        var tmpName = nameArray[tmpIdx]
        if(tmpIdx==6) tmpName=intent.getStringExtra("cateName").toString()
        initCategory(colorArray[tmpIdx], tmpName)

        binding.calendarListDetailItemSet.setOnClickListener {
            val bottomDialogFragment = CalendarListBottomDialog{
                when(it){
                    0 -> {
                        val intent = Intent(this, CalendarAddActivity::class.java)
                        intent.putExtra("calendarId",calendarId)
                        intent.putExtra("cateIdx", tmpIdx)
                        intent.putExtra("cateName", tmpName)
                        intent.putExtra("title", title)
                        intent.putExtra("datetime", datetime)
                        intent.putExtra("memo", memo)
                        startActivity(intent)
                    }
                    1 -> {
                        showLoadingDialog(this@CalendarListDetailActivity)
                        CalendarService(this@CalendarListDetailActivity).tryDeleteCalendar(roomId, calendarId)
                    }
                }
            }
            bottomDialogFragment.show(supportFragmentManager, bottomDialogFragment.tag)
        }
    }

    fun initCategory(color: String, name: String){
        binding.calendarListDetailCategory.calendarCategoryColorMini.background.setTint(Color.parseColor(color))
        binding.calendarListDetailCategory.calendarCategoryNameMini.text=name
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
        val intent = Intent(this, CalendarListActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("year", datetime.substring(0,4))
        intent.putExtra("month", datetime.substring(5,7))
        intent.putExtra("day", datetime.substring(8,10))
        startActivity(intent)
        finish()
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
        TODO("Not yet implemented")
    }

    override fun onGetDetailCalendarFailure(message: String) {
        TODO("Not yet implemented")
    }
}