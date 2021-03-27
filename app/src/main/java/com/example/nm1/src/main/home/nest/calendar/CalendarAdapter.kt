package com.example.nm1.src.main.home.nest.calendar

import android.app.ActionBar
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nm1.R
import com.example.nm1.src.main.home.HomeNestMemberAdapter
import com.example.nm1.src.main.home.model.NestInfo
import com.example.nm1.src.main.home.nest.calendar.model.CalendarInfo
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CalendarAdapter(val context: Context, val calendarLayout: LinearLayout, val date : Date, private val calendarList: List<CalendarInfo>) :
    RecyclerView.Adapter<CalendarAdapter.Holder>() {
    var dateList: ArrayList<Int> = arrayListOf()
    var cateList: ArrayList<CalendarInfo?> = arrayListOf()

    // 날짜 리스트 세팅
    var calendarDay: CalendarDay = CalendarDay(date)
    init {
        calendarDay.initBaseCalendar(calendarList)
        dateList = calendarDay.dateList
        cateList = calendarDay.cateList
    }

    // 날짜 클릭 시
    interface ItemClick {
        fun onClick(view: View, position: Int)
    }
    var itemClick: ItemClick? = null

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val datetext : TextView = itemView.findViewById(R.id.calendar_day)
        private val firstDateIndex = calendarDay.prevTail
        private val lastDateIndex = dateList.size - calendarDay.nextHead - 1
        private val cates = itemView.findViewById<RecyclerView>(R.id.calendar_category_list)

        fun bind(cate: CalendarInfo?, day : Int, position: Int) {
            // 날짜 표시
            datetext.text = day.toString()

            // 오늘 날짜 처리
            var dateString: String = SimpleDateFormat("dd.HH", Locale.KOREA).format(date)
            var hourInt = dateString.substring(3).toInt()
            var dateInt = dateString.substring(0,2).toInt()

//            if(hourInt+9>=24) dateInt+=1
            if (dateList[position] == dateInt) {
                datetext.setTextColor(itemView.resources.getColor(R.color.white))
                datetext.background.setTint(itemView.resources.getColor(R.color.orange))
            }

            // 현재 월의 1일 이전, 현재 월의 마지막일 이후 값의 텍스트를 무색처리
            if (position < firstDateIndex || position > lastDateIndex) {
                datetext.setTextColor(itemView.resources.getColor(R.color.nothing))
            }

            //
            if(cate!=null){
                val adapter = CalendarCategoryAdapter(context, cate.categories)
                cates.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                cates.adapter = adapter
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.calendar_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return dateList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(cateList[position], dateList[position], position)

        // list_item_calendar 높이 지정
        val h = calendarLayout.height / 6
        holder.itemView.minimumHeight=h

        if (itemClick != null) {
            holder?.itemView?.setOnClickListener { v ->
                itemClick?.onClick(v, position)
            }
        }
    }
}
