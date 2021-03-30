package com.nestmate.nm1.src.main.home.nest.calendar

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nestmate.nm1.R
import com.nestmate.nm1.src.main.home.nest.calendar.model.CalendarDetailInfo
import kotlinx.android.synthetic.main.calendar_category_mini.view.*


class CalendarListAdapter (val context : Context, private val calendarDetailList: MutableList<CalendarDetailInfo>) :
    RecyclerView.Adapter<CalendarListAdapter.Holder>(){

    private val colorArray = arrayOf("#0b70c6", "#1bcbb0", "#95f288", "#fcd60a", "#f26317", "#b71bcb", "#94f5e6")
    private val nameArray = arrayOf("여행", "외식", "회의", "생일", "일반", "기타", "직접 입력")

    interface ItemSetClick {
        fun onClick(view: View, position: Int)
    }
    var itemSetClick: ItemSetClick? = null

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }
    var itemClick: ItemClick? = null

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title : TextView = itemView.findViewById(R.id.calendar_list_item_title_txt);
        private val time : TextView = itemView.findViewById(R.id.calendar_list_item_time_txt);
        private val memo : TextView = itemView.findViewById(R.id.calendar_list_item_memo_txt);
        private val cate : LinearLayout = itemView.findViewById(R.id.calendar_list_category)
        val deleteBtn : ImageButton = itemView.findViewById(R.id.calendar_list_item_set)
        fun bind (calendarDetail: CalendarDetailInfo) {
            title.text =calendarDetail.title
            // 시간
            val datetime = calendarDetail.time
            var month=datetime.substring(5, 7); var day=datetime.substring(8, 10)
            var hour=datetime.substring(11, 13).toInt(); var min=datetime.substring(14, 16)
            var prefix = "오전 "
            if(hour>=12){//이게 맞나...?
                if(hour!=12) hour-=12
                prefix="오후 "
            }
            time.text=month+"월 "+day+"일 "+prefix+hour.toString()+"시 "+min+"분"
            memo.text=calendarDetail.content
            initCategory(cate, calendarDetail.categoryIdx, calendarDetail.category)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.calendar_list_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return calendarDetailList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(calendarDetailList[position])

        if (itemSetClick != null) {
            holder.deleteBtn.setOnClickListener { v ->
                itemSetClick?.onClick(v, position)
            }
        }

        if (itemClick != null) {
            holder.itemView.setOnClickListener { v ->
                itemClick?.onClick(v, position)
            }
        }
    }

    fun initCategory(cate: View, idx: Int, name:String?){
        cate.calendar_category_color_mini.background.setTint(Color.parseColor(colorArray[idx]))
        if(idx==6) cate.calendar_category_name_mini.text=name
        else cate.calendar_category_name_mini.text=nameArray[idx]
    }
}