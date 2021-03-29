package com.example.nm1.src.main.home.nest.calendar

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.nm1.R
import com.example.nm1.config.ApplicationClass
import com.example.nm1.config.BaseActivity
import com.example.nm1.databinding.ActivityCalendarAddBinding
import com.example.nm1.src.main.home.nest.calendar.model.*
import com.example.nm1.util.onMyTextChanged
import kotlinx.android.synthetic.main.activity_calendar_add.*
import kotlinx.android.synthetic.main.activity_calendar_add.view.*
import kotlinx.android.synthetic.main.calendar_category.view.*
import kotlinx.android.synthetic.main.register_one.*
import kotlinx.android.synthetic.main.toolbar_back_plus.view.*
import java.text.SimpleDateFormat
import java.util.*


class CalendarAddActivity : BaseActivity<ActivityCalendarAddBinding>(ActivityCalendarAddBinding::inflate), CalendarActivityView {

    // 전역 변수
    private var selecteddate: Date? = null
    private var selectedtime: Date? = null
    // 수정일 경우 미리 세팅할려고
    private var preYear: Int = -1
    private var preMonth: Int = -1
    private var preDay: Int = -1
    private var preHour: Int = -1
    private var preMin: Int = -1
    // 일정 목록으로 갈 때 넘겨줘야 할 정보들
    private var moveYear: Int = -1
    private var moveMonth: Int = -1
    private var moveDay: Int = -1
    // 서버에 넘겨야 할 때 부가적으로 필요한 정보들
    private var moveHour: Int  = -1
    private var moveMin: Int  = -1
    private var moveTitle : String = ""
    private var moveMemo : String = ""
    private var moveCate : String = ""
    private var moveCateIdx : Int = -1
    // 확인 작업
    private var isOK = false
    private val Selected = Array(4){0}
    // 부수 데이터
    private var calendarId : Int = -1
    private val roomId = ApplicationClass.sSharedPreferences.getInt("roomId", 0)
    private val colorArray = arrayOf("#0b70c6", "#1bcbb0", "#95f288", "#fcd60a", "#f26317", "#b71bcb", "#94f5e6")
    private val nameArray = arrayOf("여행", "외식", "회의", "생일", "일반", "기타", "직접 입력")
    val Int.dp: Int
        get() {
            val metrics = resources.displayMetrics
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), metrics).toInt()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val cateArray = arrayOf(category_trip, category_meal, category_meeting, category_birthday, category_general, category_etc, category_random)

        //init
        //일정 추가가 아닌 수정일 경우!
        for(idx in cateArray.indices) initCategory(cateArray[idx], colorArray[idx], nameArray[idx])
        if(intent.hasExtra("calendarId")){
            calendarId=intent.getIntExtra("calendarId", 0)
            binding.calendarAddToolbar.toolbarTitle.text="일정수정"
            //카테고리 인덱스 및 이름
            val tmpIdx = intent.getIntExtra("cateIdx", -1)
            moveCateIdx=tmpIdx
            if(tmpIdx==6) changeRandomCate(intent.getStringExtra("cateName").toString())
            else moveCate=nameArray[tmpIdx]
            changeStroke(tmpIdx, cateArray)
            //시간
            val datetime = intent.getStringExtra("datetime")
            var prefix = "오전 "
            if (datetime != null) {
                preYear=datetime.substring(0, 4).toInt()
                preMonth=datetime.substring(5, 7).toInt()
                preDay=datetime.substring(8, 10).toInt()
                preHour=datetime.substring(11, 13).toInt()
                if(preHour>=12){//이게 맞나...?
                    if(preHour!=12) preHour-=12
                    prefix="오후 "
                }
                preMin=datetime.substring(14, 16).toInt()
                binding.calAddDate.text=preYear.toString()+"년 "+preMonth.toString()+"월 "+preDay.toString()+"일"
                binding.calAddTime.text=prefix+preHour.toString()+"시 "+preMin.toString()+"분"
            }
            moveYear=preYear; moveMonth=preMonth; moveDay=preDay; moveHour=preHour; moveMin=preMin;
            // 제목
            binding.calendarTitleTxt.setText(intent.getStringExtra("title").toString())
            moveTitle=intent.getStringExtra("title").toString()
            // 메모
            binding.calendarContentTxt.setText(intent.getStringExtra("memo").toString())
            moveMemo=intent.getStringExtra("memo").toString()
            Selected[0]=1;Selected[1]=1;Selected[2]=1;Selected[3]=1;
            checkActive()
        }
        else{ //일정 추가일 경우!
            if(intent.hasExtra("year")) {
                Selected[1]=1
                preYear= intent.getStringExtra("year")!!.toInt()
                preMonth= intent.getStringExtra("month")!!.toInt()
                preDay= intent.getStringExtra("day")!!.toInt()
                moveYear=preYear; moveMonth=preMonth; moveDay=preDay;
                binding.calAddDate.text = preYear.toString()+"년 "+preMonth.toString()+"월 "+preDay.toString()+"일"
            }
            binding.calendarAddToolbar.toolbarTitle.text ="일정추가"
        }

        //카테고리 선택
        //1
        binding.calendarCategoryPick1.category_trip.setOnClickListener {
            changeStroke(0, cateArray)
        }
        binding.calendarCategoryPick1.category_meal.setOnClickListener {
            changeStroke(1, cateArray)
        }
        binding.calendarCategoryPick1.category_meeting.setOnClickListener {
            changeStroke(2, cateArray)
        }
        binding.calendarCategoryPick1.category_birthday.setOnClickListener {
            changeStroke(3, cateArray)
        }
        //2
        binding.calendarCategoryPick2.category_general.setOnClickListener {
            changeStroke(4, cateArray)
        }
        binding.calendarCategoryPick2.category_etc.setOnClickListener {
            changeStroke(5, cateArray)
        }
        binding.calendarCategoryPick2.category_random.setOnClickListener {
            changeStroke(6, cateArray)
            CalendarAddDialog().show(supportFragmentManager, "CalendarAddDialog")
        }

        //날짜 선택
        binding.calAddDate.setOnClickListener {
//            datepickerdialog에 표시할 달력
            val datepickercalendar = Calendar.getInstance()
            var year = datepickercalendar.get(Calendar.YEAR)
            if(moveYear!=-1) year= moveYear
            var month = datepickercalendar.get(Calendar.MONTH)
            if(moveMonth!=-1) month= moveMonth-1
            var day = datepickercalendar.get(Calendar.DAY_OF_MONTH)
            if(moveDay!=-1) day= moveDay

            val dpd = DatePickerDialog(
                this, R.style.MyDatePickerStyle, { _, year, monthOfYear, dayOfMonth ->
//                  월이 0부터 시작하여 1을 더해주어야함
                    val month = monthOfYear + 1
//                   선택한 날짜의 요일을 구하기 위한 calendar
                    val calendar = Calendar.getInstance()
//                    선택한 날짜 세팅
                    calendar.set(year, monthOfYear, dayOfMonth)
                    selecteddate = calendar.time

                    val simpledateformat = SimpleDateFormat("EEEE", Locale.KOREA)

                    binding.calAddDate.text = year.toString()+"년 "+month.toString()+"월 "+dayOfMonth.toString()+"일"
                    Selected[1]=binding.calAddDate.text.toString().length
                    Log.d("one", Selected[1].toString()+" "+binding.calAddDate.text.toString().length)
                    checkActive()
                    moveYear=year
                    moveMonth=month
                    moveDay=dayOfMonth
                },
                year,
                month,
                day
            )
//           최소 날짜를 현재 시각 이후로
            dpd.datePicker.minDate = System.currentTimeMillis() - 1000
            dpd.show()
        }

        //시간 선택
        binding.calAddTime.setOnClickListener {
            //timepickerdialog에 표시할 달력
            val timepickercalendar = Calendar.getInstance()
            var hour = timepickercalendar.get(Calendar.HOUR_OF_DAY)
            if(moveHour!=-1) hour= moveHour
            var minute = timepickercalendar.get(Calendar.MINUTE)
            if(moveMin!=-1) minute= moveMin
            val tpd = TimePickerDialog(
                this, R.style.MyTimePickerStyle, { _, hourOfDay, minute ->
                    val calendar = Calendar.getInstance()
//                    선택한 날짜 세팅
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minute)
                    selectedtime = calendar.time

                    var timeString: String = if (hourOfDay < 12)
                        "오전 " + hourOfDay.toString() + "시 " + minute.toString() + "분"
                    else {
                        if(hourOfDay == 12) "오후 " + hourOfDay.toString() + "시 " + minute.toString() + "분"
                        else "오후 " + (hourOfDay - 12).toString() + "시 " + minute.toString() + "분"
                    }
                    binding.calAddTime.text = timeString
                    Selected[2]=timeString.length
                    checkActive()
                    Log.d("two", Selected[2].toString()+" "+timeString.length)
                    moveHour=hourOfDay
                    moveMin=minute
                },
                hour,
                minute,
                false
            )
            tpd.show()
        }

        //제목 입력
        binding.calendarTitleTxt.onMyTextChanged {
            moveTitle=binding.calendarTitleTxt.text.toString()
            if(moveTitle.trim()!="") Selected[3]=binding.calendarTitleTxt.text.length
            else Selected[3]=0
            checkActive()
        }

        binding.calendarContentTxt.onMyTextChanged {
            if(binding.calendarContentTxt.lineCount>10){
                binding.calendarContentTxt.text.delete(binding.calendarContentTxt.selectionEnd -1, binding.calendarContentTxt.selectionStart)
                val imm = this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.calendarContentTxt.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS)
            }
            else if(binding.calendarContentTxt.text.toString().trim()!="") moveMemo=binding.calendarContentTxt.text.toString()
        }

        binding.calendarAddBtn.setOnClickListener {
            checkActive()
            if(isOK) {
                if(calendarId!=-1){
                    val datetime = "$moveYear/$moveMonth/$moveDay/$moveHour/$moveMin"
                    val putArgu = PutCalendarRequest(moveTitle, moveMemo, datetime, moveCate, moveCateIdx)
                    showLoadingDialog(this@CalendarAddActivity)
                    CalendarService(this).tryPutCalendar(roomId, calendarId, putArgu)
                }
                else{
                    val datetime = "$moveYear/$moveMonth/$moveDay/$moveHour/$moveMin"
                    val postArgu = PostAddCalendarRequest(moveTitle, moveMemo, datetime, moveCate, moveCateIdx)
                    showLoadingDialog(this@CalendarAddActivity)
                    CalendarService(this).tryAddCalendar(roomId, postArgu)
                }
            }
        }
    }

    fun initCategory(cate: View, color: String, name: String){
        cate.calendar_category_color.background.setTint(Color.parseColor(color))
        cate.calendar_category_name.text=name
    }

    fun changeStroke(selectedIdx: Int, arr:Array<View>){
        //초기화
        val gd_old = GradientDrawable()
        gd_old.setColor(Color.WHITE)
        gd_old.cornerRadius=30.dp.toFloat()
        gd_old.setStroke(2, resources.getColor(R.color.light_gray))
        for(idx in arr.indices) arr[idx].background=gd_old

        //선택된 카테고리
        val gd_new = GradientDrawable()
        gd_new.setColor(Color.WHITE)
        gd_new.cornerRadius=30.dp.toFloat()
        gd_new.setStroke(2, Color.parseColor(colorArray[selectedIdx]))
        arr[selectedIdx].background=gd_new

        moveCateIdx=selectedIdx
        moveCate=nameArray[selectedIdx]

        Selected[0]=selectedIdx
        checkActive()
    }

    fun changeRandomCate(new :String){
        if(new.trim()!="") {
            binding.calendarCategoryPick2.category_random.calendar_category_name.text=new
            moveCate = new
        }
    }

    fun checkActive(){
        for(idx in Selected.indices){
            if(Selected[idx]==0){
                Log.d("flag", idx.toString())
                binding.calendarAddBtn.setBackgroundResource(R.drawable.roundrec_design_inactive_bg)
                isOK=false
                return
            }
        }
        binding.calendarAddBtn.setBackgroundResource(R.drawable.roundrec_design_active_bg)
        isOK = true
    }

    override fun onAddCalendarSuccess(response: AddCalendarResponse) {
        dismissLoadingDialog()
        val intent = Intent(this, CalendarListActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("year", moveYear.toString())
        intent.putExtra("month", moveMonth.toString())
        intent.putExtra("day", moveDay.toString())
        startActivity(intent)
        finish()
    }

    override fun onAddCalendarFailure(message: String) {
        showCustomToast("다시 시도 해주세요.")
    }

    override fun onPutCalendarSuccess(response: PutCalendarResponse) {
        dismissLoadingDialog()
        val intent = Intent(this, CalendarListActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("year", moveYear.toString())
        intent.putExtra("month", moveMonth.toString())
        intent.putExtra("day", moveDay.toString())
        startActivity(intent)
        finish()
    }

    override fun onPutCalendarFailure(message: String) {
        showCustomToast("다시 시도 해주세요.")
    }

    override fun onDeleteCalendarSuccess(response: DeleteCalendarResponse) {
        TODO("Not yet implemented")
    }

    override fun onDeleteCalendarFailure(message: String) {
        TODO("Not yet implemented")
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