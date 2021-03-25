package com.example.nm1.src.main.home.nest.notice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nm1.R

class NoticeVoteRVAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var dataList = ArrayList<NoticeVoteData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            0 -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.notice_vote_rv_item, parent, false)
                return NoticeRVHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.notice_vote_rv_item, parent, false)
                return VoteRVHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is NoticeRVHolder -> {
                holder.bindWithView(this.dataList[position])
            }
            is VoteRVHolder -> {
                holder.bindWithView(this.dataList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun submitList(list: ArrayList<NoticeVoteData>){
        this.dataList = list
    }

    override fun getItemViewType(position: Int): Int {
        if(dataList[position].isNotice){
            return 0
        }else{
            return 1
        }
    }
}