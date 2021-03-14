package com.example.nm1.src.main.home.nest.notice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nm1.R

class NoticeAdapter (private val noticeList: ArrayList<Notice>) :
    RecyclerView.Adapter<NoticeAdapter.Holder>() {
    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val contents : TextView = itemView.findViewById(R.id.nvContent);
        private val numbers : TextView = itemView.findViewById(R.id.nvLabel);
        fun bind (notice: Notice) {
            numbers.text =notice.NoticeNumber
            contents.text = notice.NoticeContent
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.nv_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return noticeList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(noticeList[position])
    }
}