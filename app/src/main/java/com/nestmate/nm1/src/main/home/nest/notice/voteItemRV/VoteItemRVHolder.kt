package com.nestmate.nm1.src.main.home.nest.notice.voteItemRV

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.vote_add_rv_item.view.*

class VoteItemRVHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private var num = itemView.vote_add_rv_item_num
    private var content = itemView.vote_add_rv_item_content

    fun bindWithView(item: VoteItemData){
        num.text = item.num + "."
        content.text = item.content
    }
}