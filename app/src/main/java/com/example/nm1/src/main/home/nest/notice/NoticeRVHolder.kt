package com.example.nm1.src.main.home.nest.notice

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nm1.R
import kotlinx.android.synthetic.main.notice_vote_rv_item.view.*

class NoticeRVHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private var img = itemView.notice_vote_rv_item_img
    private var title = itemView.notice_vote_rv_item_title
    private var content = itemView.notice_vote_rv_item_content
    private var timestamp = itemView.notice_vote_rv_item_timestamp

    fun bindWithView(item: NoticeVoteData){
        Glide.with(itemView).load(item.ownerImage).error(R.drawable.chicken_img).into(img)
        if(item.isNotice){
            title.text = "공지"
        }else{
            title.text = "투표"
        }

        content.text = item.content
        timestamp.text = item.timestamp
    }
}