package com.nestmate.nm1.src.main.home.nest.member

import android.content.Context
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nestmate.nm1.R
import com.nestmate.nm1.src.main.home.nest.member.model.Member

class MemberAdapter(val context: Context, private val memList: List<Member>, val fragmentManager: FragmentManager):
    RecyclerView.Adapter<MemberAdapter.ItemViewHolder>(){

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName = itemView.findViewById<TextView>(R.id.mem_tv_memname)
        private val imgProfile = itemView.findViewById<ImageView>(R.id.mem_img_profile)
        private val btnMenu = itemView.findViewById<Button>(R.id.mem_btn_edit)

        fun bind(member: Member, context: Context) {
            Glide.with(context)
                .load(member.profileImg)
                .into(imgProfile)
            tvName.text = member.nickname //멤버이름

            btnMenu.setOnClickListener {
                val exitBottomSheet = MemberExitBottomSheet()
                exitBottomSheet.show(fragmentManager, exitBottomSheet.tag)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MemberAdapter.ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.mem_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: MemberAdapter.ItemViewHolder, position: Int) {
        holder.bind(memList[position], context)
        if (position==0)
            holder.itemView.findViewById<Button>(R.id.mem_btn_edit).visibility = View.VISIBLE
    }

    override fun getItemCount(): Int {
        return memList.size
    }
}