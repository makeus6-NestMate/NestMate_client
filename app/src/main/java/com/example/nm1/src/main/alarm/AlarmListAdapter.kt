package com.example.nm1.src.main.alarm

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nm1.R
import com.example.nm1.src.main.alarm.model.AlarmInfo
import com.example.nm1.src.main.home.nest.calendar.CalendarListAdapter
import com.example.nm1.src.main.home.nest.calendar.model.CalendarDetailInfo
import com.example.nm1.src.main.home.nest.chart.model.ChartMemberInfo

class AlarmListAdapter (val context : Context, private val alarmList: List<AlarmInfo>):
    RecyclerView.Adapter<AlarmListAdapter.Holder>(){

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val img : ImageView = itemView.findViewById(R.id.alarm_img);
        private val content : TextView = itemView.findViewById(R.id.alarm_content);
        private val time : TextView = itemView.findViewById(R.id.alarm_time);
        fun bind (alarmInfo: AlarmInfo, context: Context) {
            //이미지
            if(alarmInfo.profileImg!=null) Glide.with(context).load(alarmInfo.profileImg).into(img)
            else img.setImageResource(R.drawable.home_bird_icon)
            //내용
            val splitArr = alarmInfo.message.split(' ')
            val firstLen = splitArr[0].length // 첫 단어는 첫째줄, 나머지는 둘째줄
            content.text=alarmInfo.message.substring(0,firstLen)+'\n'+alarmInfo.message.substring(firstLen+1)
            //시간 마킹
            var hour = alarmInfo.createdAt.substring(11,13).toInt()
            val min = alarmInfo.createdAt.substring(14,16).toInt()
            var prefix = "오전 "
            if(hour>=12){
                prefix="오후 "
                if(hour>12) hour-=12
            }
            time.text = prefix+hour.toString()+"시 "+min.toString()+"분"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.alarm_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return alarmList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(alarmList[position], context)
    }
}