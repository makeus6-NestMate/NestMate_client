package com.example.nm1.src.main.home.nest.rule

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.rule_rv_item.view.*

class RuleRVHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private var num = itemView.rule_rv_item_num
    private var content = itemView.rule_rv_item_content
    private var more_btn = itemView.rule_rv_item_more_btn

    fun binWithView(item: RuleData){
        num.text = item.num
        content.text = item.content
    }
}