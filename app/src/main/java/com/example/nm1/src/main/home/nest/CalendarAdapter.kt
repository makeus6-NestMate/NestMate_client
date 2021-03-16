package com.example.nm1.src.main.home.nest

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nm1.R
import kotlinx.android.synthetic.main.calendar_item.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CalendarAdapter(val context: Context, val calendarLayout: LinearLayout, val date: Date) :
    RecyclerView.Adapter<CalendarAdapter.CalendarItemHolder>() {
    var dataList: ArrayList<Int> = arrayListOf()

    // 날짜 리스트 세팅
    var calendarDay: CalendarDay = CalendarDay(date)
    init {
        calendarDay.initBaseCalendar()
        dataList = calendarDay.dateList
    }

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    override fun onBindViewHolder(holder: CalendarItemHolder, position: Int) {

        // list_item_calendar 높이 지정
        val h = calendarLayout.height / 6
        holder.itemView.layoutParams.height = h

        holder?.bind(dataList[position], position, context)
        if (itemClick != null) {
            holder?.itemView?.setOnClickListener { v ->
                itemClick?.onClick(v, position)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarItemHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.calendar_item, parent, false)
        return CalendarItemHolder(view)
    }

    override fun getItemCount(): Int = dataList.size

    inner class CalendarItemHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        var itemCalendarDateText: TextView = itemView!!.calendar_day

        fun bind(data: Int, position: Int, context: Context) {
            val firstDateIndex = calendarDay.prevTail
            val lastDateIndex = dataList.size - calendarDay.nextHead - 1

            // 날짜 표시
            itemCalendarDateText.setText(data.toString())

            // 오늘 날짜 처리
            var dateString: String = SimpleDateFormat("dd", Locale.KOREA).format(date)
            var dateInt = dateString.toInt()
            if (dataList[position] == dateInt) {
                itemCalendarDateText.setTextColor(itemView.resources.getColor(R.color.white))
                itemCalendarDateText.setBackgroundColor(itemView.resources.getColor(R.color.orange))
            }

            // 현재 월의 1일 이전, 현재 월의 마지막일 이후 값의 텍스트를 무색처리
            if (position < firstDateIndex || position > lastDateIndex) {
                itemCalendarDateText.setTextColor(itemView.resources.getColor(R.color.nothing))
            }
        }

    }
}
