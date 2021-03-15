package com.example.nm1.src.main.home.nest

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.example.nm1.R
import com.example.nm1.config.BaseActivity
import com.example.nm1.databinding.CalendarAddBinding
import kotlinx.android.synthetic.main.calendar_activity.*
import kotlinx.android.synthetic.main.calendar_activity.calendar_toolbar
import kotlinx.android.synthetic.main.calendar_add.*
import kotlinx.android.synthetic.main.calendar_add.view.*
import kotlinx.android.synthetic.main.calendar_category.view.*
import kotlinx.android.synthetic.main.chart_activity.*
import kotlinx.android.synthetic.main.activity_nest.*
import kotlinx.android.synthetic.main.toolbar_back.*
import kotlinx.android.synthetic.main.toolbar_back_plus.view.*

class CalendarAdd : BaseActivity<CalendarAddBinding>(CalendarAddBinding::inflate) {

    val colorArray = arrayOf("#0b70c6", "#1bcbb0", "#95f288", "#fcd60a", "#f26317","#b71bcb","#94f5e6")
    val nameArray = arrayOf("여행", "외식", "회의", "생일", "일반", "기타", "직접 입력")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calendar_add)

        val cateArray = arrayOf(category_trip, category_meal, category_meeting, category_birthday, category_general, category_etc, category_random)

        //
        //init
        calendar_add_toolbar.toolbar_title.text="일정추가"
        for(color in colorArray){
            for(cate in cateArray) initCategory(cate, color, null)
        }
        for(name in nameArray){
            for(cate in cateArray) initCategory(cate, null, name)
        }

        //event
        back_btn.setOnClickListener {
            finish()
        }
    }

    fun initCategory(cate : View, color:String?, name:String?){
        cate.setBackgroundColor(resources.getColor(R.color.white))
        if(name==null) cate.calendar_category_color.setBackgroundColor(Color.parseColor(color))
        else cate.calendar_category_name.text=name
    }
}