package com.example.nm1.src.main.home.nest.rule

import android.content.Context
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.example.nm1.R
import com.example.nm1.src.main.MainActivity
import com.example.nm1.src.main.home.nest.rule.model.PostRuleRequest
import kotlinx.android.synthetic.main.rule_rv_item.view.*

class RuleRVAdapter: RecyclerView.Adapter<RuleRVHolder>(){
    private var list = ArrayList<RuleData>()
    private var listener: OnItemClickListener? = null
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RuleRVHolder {
        return RuleRVHolder(LayoutInflater.from(parent.context).inflate(R.layout.rule_rv_item, parent, false))
    }

    override fun onBindViewHolder(holder: RuleRVHolder, position: Int) {
        holder.binWithView(list[position])
        holder.itemView.rule_rv_item_more_btn.setOnClickListener {
            listener!!.onClicked(position, (position+1).toString(), list[position].ruleId)
        }

        if(list[position].isOwner == "Y"){
            holder.itemView.rule_rv_item_more_btn.visibility = View.VISIBLE
        }else{
            holder.itemView.rule_rv_item_more_btn.visibility = View.GONE
        }

        holder.itemView.rule_rv_item_content_et.setOnKeyListener(object: View.OnKeyListener{
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if((event?.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){

                    val str = holder.itemView.rule_rv_item_content_et.text.toString()
                    listener!!.onEdited(str, position, list[position].ruleId, list[position].isOwner)

                    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(holder.itemView.rule_rv_item_content_et.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS)

                    return true
                }
                return false
            }

        })
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun submitList(list: ArrayList<RuleData>, context: Context){
        this.list = list
        this.context = context
    }

    fun setOnClickListener(listener: OnItemClickListener){
        this.listener = listener
    }

    interface OnItemClickListener{
        fun onClicked(position: Int, ruleNum: String, ruleId: Int)
        fun onEdited(content: String, position: Int, ruleID: Int, isOwner: String)
    }

    fun editItem(position: Int, ruleId: Int) {
        Log.d("로그", "Int Adapter pos:${position.toString()} / ruleId: ${ruleId.toString()}")
    }

}