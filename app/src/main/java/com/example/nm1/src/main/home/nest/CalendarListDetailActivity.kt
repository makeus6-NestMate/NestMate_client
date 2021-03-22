package com.example.nm1.src.main.home.nest

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.nm1.config.BaseActivity
import com.example.nm1.databinding.ActivityCalendarListBinding
import com.example.nm1.databinding.ActivityCalendarListDetailBinding
import kotlinx.android.synthetic.main.activity_calendar_add.view.*
import kotlinx.android.synthetic.main.calendar_category.view.*

class CalendarListDetailActivity : BaseActivity<ActivityCalendarListDetailBinding>(
    ActivityCalendarListDetailBinding::inflate) {

    private val colorArray = arrayOf("#0b70c6", "#1bcbb0", "#95f288", "#fcd60a", "#f26317", "#b71bcb", "#94f5e6")
    private val nameArray = arrayOf("여행", "외식", "회의", "생일", "일반", "기타", "직접 입력")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val position = intent.getIntExtra("position", 0)
        binding.calendarListDetailToolbar.toolbarTitle.text=intent.getStringExtra("toolbar")
        val title = intent.getStringExtra("title")
        binding.calendarListDetailTitle.text=title
        val date=intent.getStringExtra("date")
        val time=intent.getStringExtra("time")
        binding.calendarListDetailItemTimeTxt.text=date+time
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
                        intent.putExtra("position",position)
                        intent.putExtra("cateIdx", tmpIdx)
                        intent.putExtra("cateName", tmpName)
                        intent.putExtra("title", title)
                        intent.putExtra("date", date)
                        intent.putExtra("time", time)
                        intent.putExtra("memo", memo)
                        startActivity(intent)
                    }
                    1 -> Toast.makeText(this, "삭제해야합니다", Toast.LENGTH_LONG)
                }
            }
            bottomDialogFragment.show(supportFragmentManager, bottomDialogFragment.tag)
        }
    }

    fun initCategory(color: String, name: String){
        binding.calendarListDetailCategory.calendarCategoryColorMini.background.setTint(Color.parseColor(color))
        binding.calendarListDetailCategory.calendarCategoryNameMini.text=name
    }
}