package com.example.nm1.src.main.home.nest.notice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nm1.R
import com.example.nm1.src.main.home.nest.notice.model.NoticeVoteInfo
import com.example.nm1.src.main.home.nest.rule.RuleRVAdapter
import kotlinx.android.synthetic.main.notice_vote_rv_item.view.*

class NoticeVoteRVAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var dataList = ArrayList<NoticeVoteInfo>()
    private var listener: NoticeVoteRVAdapter.OnItemClickListener? = null

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
                holder.itemView.notice_vote_rv_item_more_btn.setOnClickListener {
                    listener!!.onNoticeClicked(position, dataList[position].noticeId!!)
                }
            }
            is VoteRVHolder -> {
                holder.bindWithView(this.dataList[position])
                holder.itemView.notice_vote_rv_item_more_btn.setOnClickListener {
                    listener!!.onVoteClicked(position, dataList[position].voteId!!)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun submitList(list: ArrayList<NoticeVoteInfo>){
        this.dataList = list
    }

    fun setOnClickListener(listener: NoticeVoteRVAdapter.OnItemClickListener){
        this.listener = listener
    }

    override fun getItemViewType(position: Int): Int {
        if(dataList[position].isNotice == "Y"){
            return 0
        }else{
            return 1
        }
    }

    interface OnItemClickListener {
        fun onNoticeClicked(position: Int, noticeId: Int)
        fun onVoteClicked(position: Int, voteId: Int)
    }
}