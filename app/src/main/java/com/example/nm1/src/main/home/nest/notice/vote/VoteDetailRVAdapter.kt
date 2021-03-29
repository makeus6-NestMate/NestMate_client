package com.example.nm1.src.main.home.nest.notice.vote

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nm1.R
import com.example.nm1.src.main.home.nest.notice.vote.model.Choice
import kotlinx.android.synthetic.main.vote_rv_item.view.*

class VoteDetailRVAdapter: RecyclerView.Adapter<VoteDetailRVAdapter.VoteDetailRVHolder>() {
    private var dataList = ArrayList<Choice>()
    public var isFinished = "N"
    private var lastPosition = -1
    private var listener: VoteDetailRVAdapter.OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoteDetailRVHolder {
        return VoteDetailRVHolder(LayoutInflater.from(parent.context).inflate(R.layout.vote_rv_item, parent, false))
    }

    override fun onBindViewHolder(holder: VoteDetailRVHolder, position: Int) {
        holder.bindWithView(dataList[position], isFinished)
        holder.radio.isChecked = (position == lastPosition)
        holder.radio.setOnClickListener {
            listener!!.onRadioClicked(dataList[position].choiceId)
        }

        holder.layout.setOnClickListener {
            listener!!.onMemberClicked(dataList[position].choiceId)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun submitList(list: ArrayList<Choice>){
        this.dataList = list
    }

    fun updateStatus(isFinished: String){
        this.isFinished = isFinished
    }

    fun setOnClickListener(listener: VoteDetailRVAdapter.OnItemClickListener){
        this.listener = listener
    }

   inner class VoteDetailRVHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var num = itemView.vote_rv_item_num
        var title = itemView.vote_rv_item_content
        var cnt = itemView.vote_rv_item_unvoteMemberCnt
        var radio = itemView.vote_rv_item_radio_btn
        var layout = itemView.vote_rv_item_layout

        fun bindWithView(item: Choice, isFinished: String){
            num.text = (adapterPosition + 1).toString() + "."
            title.text = item.choice
            cnt.text = item.memberCnt.toString()+ " 명"
            if(isFinished == "Y"){
                cnt.visibility = View.VISIBLE
                radio.visibility = View.GONE
            }else{
                cnt.visibility = View.GONE
                radio.visibility = View.VISIBLE
            }

            radio.setOnCheckedChangeListener { buttonView, isChecked ->
                if(isChecked){
                    lastPosition = adapterPosition
                    notifyDataSetChanged()
                }
            }
        }

    }

    interface OnItemClickListener {
        fun onRadioClicked(choiceId: Int)
        fun onMemberClicked(choiceId: Int)
    }
}