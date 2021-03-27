package com.example.nm1.src.main.home.nest.calendar

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nm1.R
import com.example.nm1.src.main.home.nest.calendar.model.CalendarCategoryInfo
import kotlin.collections.ArrayList

class CalendarCategoryAdapter (val context : Context, private val calendarCateList: List<CalendarCategoryInfo>) :
    RecyclerView.Adapter<CalendarCategoryAdapter.Holder>(){

    private val colorArray = arrayOf("#0b70c6", "#1bcbb0", "#95f288", "#fcd60a", "#f26317", "#b71bcb", "#94f5e6")
    private val nameArray = arrayOf("여행", "외식", "회의", "생일", "일반", "기타", "직접 입력")

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val color : ImageView = itemView.findViewById(R.id.calendar_category_color_mini_list)
        private val name : TextView = itemView.findViewById(R.id.calendar_category_name_mini_list)
        fun bind (calCateList: CalendarCategoryInfo) {
            color.background.setTint(Color.parseColor(colorArray[calCateList.categoryIdx]))
            if(calCateList.category!=null) name.text=calCateList.category
            else name.text=nameArray[calCateList.categoryIdx]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.calendar_category_mini_list, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return calendarCateList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(calendarCateList[position])
    }
}