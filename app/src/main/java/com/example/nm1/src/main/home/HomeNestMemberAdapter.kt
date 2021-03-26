package com.example.nm1.src.main.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.nm1.R
import com.example.nm1.src.main.home.model.NestMember
import java.util.*


class HomeNestMemberAdapter(val context: Context, private val memList: List<NestMember>):
    RecyclerView.Adapter<HomeNestMemberAdapter.ItemViewHolder>(){

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName = itemView.findViewById<TextView>(R.id.nest_tv_memname)
        private val imgProfile = itemView.findViewById<ImageView>(R.id.nest_mem_img_profile)

        fun bind(member: NestMember, context: Context) {
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val width = (windowManager.defaultDisplay.width *0.1).toInt()

            itemView.layoutParams = RecyclerView.LayoutParams(
                width,
                RecyclerView.LayoutParams.MATCH_PARENT
            )

            tvName.text = member.nickname //멤버 이름
            if (member.profileImg!=""){
                Glide
                    .with(context)
                    .load(member.profileImg)
                    .apply(RequestOptions.circleCropTransform()) //원으로
                    .into(imgProfile) //멤버 프로필
            }
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

    override fun getItemCount(): Int {
        return if (memList.size>3)
            3
        else
            memList.size
    }
}