package com.example.nm1.src.main.home

import android.content.Context
import android.view.Gravity.apply
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nm1.R
import com.example.nm1.src.main.home.model.NestInfo
import com.example.nm1.src.main.home.model.NestMember
import java.util.*

class HomeNestMemberAdapter(val context: Context, private val memList: List<NestMember>):
    RecyclerView.Adapter<HomeNestMemberAdapter.ItemViewHolder>(){

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName = itemView.findViewById<TextView>(R.id.nest_tv_memname)
        private val imgProfile = itemView.findViewById<ImageView>(R.id.nest_img_memprofile)

        fun bind(member: NestMember, context: Context) {

            tvName.text = member.nickname //멤버 이름
            Glide
                .with(context)
                .load(member.profileImg)
                .into(imgProfile) //멤버 프로필
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeNestMemberAdapter.ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.nest_member_items, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeNestMemberAdapter.ItemViewHolder, position: Int) {
        holder.bind(memList[position], context)
    }

//    3명만 보여주게
    override fun getItemCount(): Int {
        return if(memList.size > 3){
            3
        } else {
            memList.size
        }
    }
}