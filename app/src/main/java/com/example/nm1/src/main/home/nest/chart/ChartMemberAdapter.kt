package com.example.nm1.src.main.home.nest.chart

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nm1.R
import com.example.nm1.src.main.home.nest.chart.model.ChartMemberInfo

class ChartMemberAdapter(val context : Context, private val chartMemList: List<ChartMemberInfo>) :
    RecyclerView.Adapter<ChartMemberAdapter.Holder>() {
    private val colorArray = arrayOf("#5e6af5", "#43a8ff", "#21ffbb", "#cfff2e","#ffe033","#ffb221","#ff7f21","#ff2121","#b70d0d")

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val img : ImageView = itemView.findViewById(R.id.chart_person_img);
        private val color : ImageView = itemView.findViewById(R.id.chart_person_color);
        private val name : TextView = itemView.findViewById(R.id.chart_person_name);
        fun bind (chartMem: ChartMemberInfo, position: Int, context: Context) {
            if(chartMem.profileImg!=null) Glide.with(context).load(chartMem.profileImg).into(img)
            else img.setImageResource(R.drawable.home_bird_icon)
            color.background.setTint(Color.parseColor(colorArray[position]))
            name.text = chartMem.nickname
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chart_member_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return chartMemList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(chartMemList[position], position, context)
    }
}