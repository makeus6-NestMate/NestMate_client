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
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.nm1.R
import kotlinx.android.synthetic.main.activity_nest.view.*
import kotlinx.android.synthetic.main.calendar_item.view.*
import kotlinx.android.synthetic.main.fragment_calendar.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CalendarAdapter(val context: Context, val calendarLayout: LinearLayout, val date: Date) :
    RecyclerView.Adapter<CalendarAdapter.Holder>() {
    var dateList: ArrayList<Int> = arrayListOf()
    var cates : ArrayList<CalendarCategory> = arrayListOf(CalendarCategory(1, null))

    // 날짜 리스트 세팅
    var calendarDay: CalendarDay = CalendarDay(date)
    init {
        calendarDay.initBaseCalendar()
        dateList = calendarDay.dateList
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

        fun bind(data: Int, position: Int) {
            // 날짜 표시
            datetext.text=data.toString()

            // 오늘 날짜 처리
            var dateString: String = SimpleDateFormat("dd", Locale.KOREA).format(date)
            var dateInt = dateString.toInt()
            if (dateList[position] == dateInt) {
                datetext.setTextColor(itemView.resources.getColor(R.color.white))
                datetext.background.setTint(itemView.resources.getColor(R.color.orange))
            }

            // 현재 월의 1일 이전, 현재 월의 마지막일 이후 값의 텍스트를 무색처리
            if (position < firstDateIndex || position > lastDateIndex) {
                datetext.setTextColor(itemView.resources.getColor(R.color.nothing))
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
        holder.bind(dateList[position], position)

        // list_item_calendar 높이 지정
        val h = calendarLayout.height / 6
        holder.itemView.layoutParams.height = h

        if (itemClick != null) {
            holder?.itemView?.setOnClickListener { v ->
                itemClick?.onClick(v, position)
            }
        }
    }
}
