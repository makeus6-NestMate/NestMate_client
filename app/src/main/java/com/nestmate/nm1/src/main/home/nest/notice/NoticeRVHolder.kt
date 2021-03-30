package com.nestmate.nm1.src.main.home.nest.notice

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nestmate.nm1.R
import com.nestmate.nm1.src.main.home.nest.notice.model.NoticeVoteInfo
import kotlinx.android.synthetic.main.notice_vote_rv_item.view.*

class NoticeRVHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private var img = itemView.notice_vote_rv_item_img
    private var title = itemView.notice_vote_rv_item_title
    private var content = itemView.notice_vote_rv_item_content
    private var timestamp = itemView.notice_vote_rv_item_timestamp

    fun bindWithView(item: NoticeVoteInfo){
        Glide.with(itemView).load(item.profileImg).error(R.drawable.chicken_img).into(img)
        if(item.isNotice == "Y"){
            title.text = "공지"
        }else{
            title.text = "투표"
        }

        content.text = item.notice
        val time = item.createdAt
        var isAm = "오전"
        var hours = "0"
        if((time.substring(9,11).toInt()) > 12){
            isAm = "오후"
            hours = (time.substring(9,11).toInt() - 12).toString()
        }else{
            isAm = "오전"
            hours = time.substring(9,11)
        }
        timestamp.text = time.substring(3, 5) + "월 " + time.substring(6,8) + "일 " + isAm + " " + hours + "시 " + time.substring(12,14) + "분"
    }
}