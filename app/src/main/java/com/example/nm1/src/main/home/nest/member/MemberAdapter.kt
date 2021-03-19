package com.example.nm1.src.main.home.nest.member

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nm1.R
import com.example.nm1.src.main.home.nest.member.model.Member
import java.util.*

class MemberAdapter(val context: Context, private val memList: List<Member>):
    RecyclerView.Adapter<MemberAdapter.ItemViewHolder>(){

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName = itemView.findViewById<TextView>(R.id.mem_tv_memname)
        private val imgProfile = itemView.findViewById<ImageView>(R.id.mem_img_profile)

        fun bind(member: Member, context: Context) {
            tvName.text = member.nickname //멤버 이름
            Glide.with(context).load(member.profileImg).into(imgProfile) //멤버 프로필
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MemberAdapter.ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.nest_member_items, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: MemberAdapter.ItemViewHolder, position: Int) {
        holder.bind(memList[position], context)
    }

//    3명만 보여주게
    override fun getItemCount(): Int {
        return memList.size
    }
}