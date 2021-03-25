package com.example.nm1.src.main.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nm1.R
import com.example.nm1.config.ApplicationClass
import com.example.nm1.src.main.home.model.NestInfo
import com.example.nm1.src.main.home.nest.NestActivity

class HomeNestAdapter(val context: Context, private val nestList: List<NestInfo>, private val fragmentManager: FragmentManager):
    RecyclerView.Adapter<HomeNestAdapter.ItemViewHolder>(){

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName = itemView.findViewById<TextView>(R.id.nest_tv_name)
        private val imgBackground = itemView.findViewById<ImageView>(R.id.nest_img_background)
        private val layoutplusMem = itemView.findViewById<ConstraintLayout>(R.id.nest_layout_plusmem)
        private val tvPlusMemNum = itemView.findViewById<TextView>(R.id.nest_tv_plusmemnum)
        private val memList = itemView.findViewById<RecyclerView>(R.id.nest_recycler_memlist)
        private val layoutNest = itemView.findViewById<ConstraintLayout>(R.id.layout_nest_item)
        val editor = ApplicationClass.sSharedPreferences.edit()
        private val viewPool = RecyclerView.RecycledViewPool()

        fun bind(nest: NestInfo, context: Context, fragmentManager: FragmentManager) {
            tvName.text = nest.roomName //둥지 이름
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val width = (windowManager.defaultDisplay.width *0.4805).toInt()

            val innerLayoutManager = GridLayoutManager(context, 3, GridLayoutManager.HORIZONTAL, false)

            memList.apply{
                layoutManager = innerLayoutManager
                adapter = HomeNestMemberAdapter(context, nest.members)
                setRecycledViewPool(viewPool)
            }

            itemView.layoutParams = RecyclerView.LayoutParams(
                width,
                RecyclerView.LayoutParams.MATCH_PARENT
            )
//           배경
            when (nest.roomColor) {
                "#" + Integer.toHexString(ContextCompat.getColor(context, R.color.peach))
                    .substring(2) -> {
                    imgBackground.setImageResource(R.drawable.dungji_peach_background)
                }
                "#" + Integer.toHexString(ContextCompat.getColor(context, R.color.light_orange))
                    .substring(
                        2
                    ) -> {
                    imgBackground.setImageResource(R.drawable.dungji_lightorange_background)
                }
                "#" + Integer.toHexString(ContextCompat.getColor(context, R.color.light_blue))
                    .substring(
                        2
                    ) -> {
                    imgBackground.setImageResource(R.drawable.dungji_lightblue_background)
                }
                "#" + Integer.toHexString(ContextCompat.getColor(context, R.color.light_purple))
                    .substring(
                        2
                    ) -> {
                    imgBackground.setImageResource(R.drawable.dungji_lightpurple_background)
                }
                "#" + Integer.toHexString(ContextCompat.getColor(context, R.color.sky))
                    .substring(2) -> {
                    imgBackground.setImageResource(R.drawable.dungji_sky_background)
                }
                "#" + Integer.toHexString(ContextCompat.getColor(context, R.color.light_green))
                    .substring(
                        2
                    ) -> {
                    imgBackground.setImageResource(R.drawable.dungji_green_background)
                }
            }
            if (nest.members.size>3){
                layoutplusMem.visibility = View.VISIBLE
                tvPlusMemNum.text = (nest.members.size-3).toString() //추가 멤버 수 보여주기
            }

            layoutNest.setOnClickListener {
//              둥지를 클릭할때마다 roomId 저장소에 저장
                editor.putString("roomName", nest.roomName)
                editor.putInt("roomId", nest.roomId)
                editor.apply()
                
                startActivity(context, Intent(context, NestActivity::class.java), null)
            }

//           둥지 수정
            layoutNest.setOnLongClickListener {
                val homeNestEditBottomSheet = HomeNestEditBottomSheet()
                editor.putString("roomName", nest.roomName)
                editor.putInt("roomId", nest.roomId)

                homeNestEditBottomSheet.show(fragmentManager, homeNestEditBottomSheet.tag)
                true
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeNestAdapter.ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.nest_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeNestAdapter.ItemViewHolder, position: Int) {
        holder.bind(nestList[position], context, fragmentManager)
    }

    override fun getItemCount(): Int {
        return nestList.size
    }
}