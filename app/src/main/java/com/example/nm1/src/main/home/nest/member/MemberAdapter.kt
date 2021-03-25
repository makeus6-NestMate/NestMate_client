package com.example.nm1.src.main.home.nest.member

import android.content.Context
import android.content.Intent
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nm1.R
import com.example.nm1.config.ApplicationClass
import com.example.nm1.src.main.home.model.NestInfo
import com.example.nm1.src.main.home.nest.NestActivity
import com.example.nm1.src.main.home.nest.member.model.Member

class MemberAdapter(val context: Context, private val memList: List<Member>):
    RecyclerView.Adapter<MemberAdapter.ItemViewHolder>(){

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName = itemView.findViewById<TextView>(R.id.mem_tv_memname)
        private val imgProfile = itemView.findViewById<ImageView>(R.id.mem_img_profile)
        private val btnMenu = itemView.findViewById<Button>(R.id.mem_btn_edit)

        fun bind(member: Member, context: Context) {
            tvName.text = member.nickname //둥지 이름
            Glide
                .with(context)
                .load(member.profileImg)
                .into(imgProfile) //멤버 프로필
            if (member.isSelf) btnMenu.visibility = View.VISIBLE
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MemberAdapter.ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.nest_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: MemberAdapter.ItemViewHolder, position: Int) {
        holder.bind(memList[position], context)
    }

    override fun getItemCount(): Int {
        return memList.size
    }
}