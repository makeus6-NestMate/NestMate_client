package com.example.nm1.src.main.home.nest.rule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nm1.R

class RuleRVAdapter: RecyclerView.Adapter<RuleRVHolder>() {
    private var list = ArrayList<RuleData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RuleRVHolder {
        return RuleRVHolder(LayoutInflater.from(parent.context).inflate(R.layout.rule_rv_item, parent, false))
    }

    override fun onBindViewHolder(holder: RuleRVHolder, position: Int) {
        holder.binWithView(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun submitList(list: ArrayList<RuleData>){
        this.list = list
    }
}