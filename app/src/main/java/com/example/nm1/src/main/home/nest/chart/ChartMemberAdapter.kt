package com.example.nm1.src.main.home.nest.chart

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nm1.R

class ChartMemberAdapter(val context : Context, private val chartMemList: ArrayList<ChartMember>) :
    RecyclerView.Adapter<ChartMemberAdapter.Holder>() {

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val img : ImageView = itemView.findViewById(R.id.chart_person_img);
        private val color : ImageView = itemView.findViewById(R.id.chart_person_color);
        private val name : TextView = itemView.findViewById(R.id.chart_person_name);
        fun bind (chartMem: ChartMember) {
//            if(chartMem.ChartMemberImg!="") img
//            else img.setImageResource(R.drawable.chicken_img)
//            Glide.with(context).load(chartMem.ChartMemberImg).into(img)
            img.setImageResource(R.drawable.chicken_img)
            color.setBackgroundColor(Color.parseColor(chartMem.ChartMemberColor))
            name.text = chartMem.ChartMemberName
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
        holder.bind(chartMemList[position])
    }
}