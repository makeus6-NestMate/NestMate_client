package com.nestmate.nm1.src.main.home.nest.notice.voteItemRV

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nestmate.nm1.R

class VoteItemRVAdapter: RecyclerView.Adapter<VoteItemRVHolder>() {
    private var dataList = ArrayList<VoteItemData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoteItemRVHolder {
        return VoteItemRVHolder(LayoutInflater.from(parent.context).inflate(R.layout.vote_add_rv_item, parent, false))
    }

    override fun onBindViewHolder(holder: VoteItemRVHolder, position: Int) {
        holder.bindWithView(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun submitList(list: ArrayList<VoteItemData>){
        this.dataList = list
    }
}