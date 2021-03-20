package com.example.nm1.src.main.home.nest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nm1.R
import kotlinx.android.synthetic.main.calendar_list_item.view.*

class CalendarListAdapter (val context : Context, private val calendarListList: ArrayList<CalendarList>) :
    RecyclerView.Adapter<CalendarListAdapter.Holder>(){

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title : TextView = itemView.findViewById(R.id.calendar_list_item_title_txt);
        private val time : TextView = itemView.findViewById(R.id.calendar_list_item_time_txt);
        private val memo : TextView = itemView.findViewById(R.id.calendar_list_item_memo_txt);
        private val deleteBtn : ImageButton = itemView.findViewById(R.id.calendar_list_item_set)
        fun bind (calendarList: CalendarList) {
            title.text =calendarList.CalListTitle
            time.text=calendarList.CalListTime
            memo.text=calendarList.CalListMemo
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.calendar_list_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return calendarListList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(calendarListList[position])
    }
}