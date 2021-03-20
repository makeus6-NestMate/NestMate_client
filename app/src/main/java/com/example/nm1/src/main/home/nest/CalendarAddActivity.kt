package com.example.nm1.src.main.home.nest

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.DrawableContainer.DrawableContainerState
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.nm1.R
import com.example.nm1.config.BaseActivity
import com.example.nm1.databinding.ActivityCalendarAddBinding
import com.example.nm1.util.onMyTextChanged
import kotlinx.android.synthetic.main.activity_calendar_add.*
import kotlinx.android.synthetic.main.activity_calendar_add.view.*
import kotlinx.android.synthetic.main.calendar_category.view.*
import kotlinx.android.synthetic.main.toolbar_back_plus.view.*
import java.text.SimpleDateFormat
import java.util.*


class CalendarAddActivity : BaseActivity<ActivityCalendarAddBinding>(ActivityCalendarAddBinding::inflate) {

    private var selecteddate: Date? = null
    private var selectedtime: Date? = null
    private var isOK = false
    private val colorArray = arrayOf(
        "#0b70c6",
        "#1bcbb0",
        "#95f288",
        "#fcd60a",
        "#f26317",
        "#b71bcb",
        "#94f5e6"
    )
    private val nameArray = arrayOf("여행", "외식", "회의", "생일", "일반", "기타", "직접 입력")
    private val Selected = Array(4){false}
    val Int.dp: Int
        get() {
            val metrics = resources.displayMetrics
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), metrics).toInt()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val cateArray = arrayOf(
            category_trip,
            category_meal,
            category_meeting,
            category_birthday,
            category_general,
            category_etc,
            category_random
        )
        //init
        binding.calendarAddToolbar.toolbarTitle.text="일정추가"
        for(idx in cateArray.indices) initCategory(cateArray[idx], colorArray[idx], nameArray[idx])

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
            val year = datepickercalendar.get(Calendar.YEAR)
            val month = datepickercalendar.get(Calendar.MONTH)
            val day = datepickercalendar.get(Calendar.DAY_OF_MONTH)

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
                    val dayName: String = simpledateformat.format(selecteddate)

                    binding.calAddDate.text = "$year.$month.$dayOfMonth ($dayName)"
                },
                year,
                month,
                day
            )
//           최소 날짜를 현재 시각 이후로
            dpd.datePicker.minDate = System.currentTimeMillis() - 1000
            dpd.show()
            checkActive()
        }

        //시간 선택
        binding.calAddTime.setOnClickListener {
            //timepickerdialog에 표시할 달력
            val timepickercalendar = Calendar.getInstance()
            val hour = timepickercalendar.get(Calendar.HOUR_OF_DAY)
            val minute = timepickercalendar.get(Calendar.MINUTE)
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
                        "오후 " + (hourOfDay - 12).toString() + "시 " + minute.toString() + "분"
                    }
                    binding.calAddTime.text = timeString
                },
                hour,
                minute,
                false
            )
            tpd.show()
            checkActive()
        }

        //제목 입력
        binding.calendarTitleTxt.onMyTextChanged {
            Selected[3] = (binding.calendarTitleTxt.text).isNotEmpty()
            checkActive()
        }

        binding.calendarAddBtn.setOnClickListener {
            checkActive()
            if(isOK){
                finish()
            }
        }
    }

    fun initCategory(cate: View, color: String, name: String){
        val drawable = resources.getDrawable(R.drawable.cal_add_cate_roundrec)
        drawable.setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_ATOP)
        cate.calendar_category_color.setImageDrawable(drawable)
        cate.calendar_category_name.text=name
    }

    fun changeStroke(selectedIdx: Int, arr:Array<View>){
        //초기화
        val gd_old = GradientDrawable()
        gd_old.setColor(Color.WHITE)
        gd_old.cornerRadius=30.dp.toFloat()
        gd_old.setStroke(1, resources.getColor(R.color.gray))
        for(idx in arr.indices) arr[idx].background=gd_old

        //선택된 카테고리
        val gd_new = GradientDrawable()
        gd_new.setColor(Color.WHITE)
        gd_new.cornerRadius=30.dp.toFloat()
        gd_new.setStroke(1, Color.parseColor(colorArray[selectedIdx]))
        arr[selectedIdx].background=gd_new

        Selected[0]=true
        checkActive()
    }

    fun changeRandomCate(new :String){
        binding.calendarCategoryPick2.category_random.calendar_category_name.text=new
    }

    fun checkActive(){
        var flag = -1
        if(selecteddate!=null) Selected[1]=true
        if(selectedtime!=null) Selected[2]=true
        for(idx in Selected.indices){
            if(!Selected[idx]) flag = idx
        }

        if(flag==-1){
            binding.calendarAddBtn.setBackgroundResource(R.drawable.roundrec_design_active_bg)
            isOK = true
        }else{
            binding.calendarAddBtn.setBackgroundResource(R.drawable.roundrec_design_inactive_bg)
        }
    }
}