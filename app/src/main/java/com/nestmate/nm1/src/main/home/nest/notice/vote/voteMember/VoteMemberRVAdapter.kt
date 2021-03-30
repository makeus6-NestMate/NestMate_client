package com.nestmate.nm1.src.main.home.nest.notice.vote.voteMember

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nestmate.nm1.R
import com.nestmate.nm1.src.main.home.nest.notice.vote.voteMember.model.VoteMember
import kotlinx.android.synthetic.main.vote__member_rv_item.view.*

class VoteMemberRVAdapter: RecyclerView.Adapter<VoteMemberRVAdapter.VoteMemberRVHolder>() {

    private var dataList = ArrayList<VoteMember>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoteMemberRVHolder {
        return VoteMemberRVHolder(LayoutInflater.from(parent.context).inflate(R.layout.vote__member_rv_item, parent, false))
    }

    override fun onBindViewHolder(holder: VoteMemberRVHolder, position: Int) {
        holder.bindWithView(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun submitList(list: ArrayList<VoteMember>){
        this.dataList = list
    }

    inner class VoteMemberRVHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private var img = itemView.vote_member_rv_item_img
        private var nickname = itemView.vote_member_rv_item_nickname

        fun bindWithView(item: VoteMember){
            //Glide.with(itemView).load(item.profileImg).error(R.drawable.chicken_img).into(img)
            Glide.with(itemView).load(item.profileImg).error(R.drawable.chicken_img).into(img)
            nickname.text = item.nickname
        }
    }
}