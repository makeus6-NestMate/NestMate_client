package com.example.nm1.src.main.home.nest.todo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nm1.R
import java.util.*
import kotlin.math.floor

class TodayTodoAdapter(val context: Context, private val todoList: ArrayList<Todo>, private val fragmentManager:FragmentManager):
    RecyclerView.Adapter<TodayTodoAdapter.ItemViewHolder>(){

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle = itemView.findViewById<TextView>(R.id.todo_tv_title)
        private val tvTime = itemView.findViewById<TextView>(R.id.todo_tv_time)
        private val tvRemain = itemView.findViewById<TextView>(R.id.todo_tv_remain)
        private val tvReaminHour = itemView.findViewById<TextView>(R.id.todo_tv_remainhour)
        private val imgTimer = itemView.findViewById<ImageView>(R.id.todo_img_timer)
        private val imgCheck = itemView.findViewById<ImageView>(R.id.todo_img_check)
        private val imgProfile = itemView.findViewById<ImageView>(R.id.todo_img_profile)
        private val imguncheckBackground = itemView.findViewById<ImageView>(R.id.todo_img_notcheckbackground)
        private val layoutCock = itemView.findViewById<LinearLayout>(R.id.todo_layout_cock)

        fun bind(todo: Todo, context: Context, fragmentManager: FragmentManager) {
//           설정된 시간
            val cal = Calendar.getInstance()
            cal.time = todo.time
            val hour = cal.get(Calendar.HOUR_OF_DAY)
            val minute = cal.get(Calendar.MINUTE)

//            현재 시간
            val current = Calendar.getInstance()

            val remainhour = hour-current.get(Calendar.HOUR_OF_DAY) //시간만뺀거
            val diffSec = (cal.timeInMillis - current.timeInMillis) / 1000
            val hourTime = floor((diffSec / 3600).toDouble()).toInt() //남은시간
            val minTime = floor(((diffSec - 3600 * hourTime) / 60).toDouble()).toInt() //남은 분

            tvTitle.text = todo.title
            tvTime.text = if (hour < 12)
                "오전 " + hour.toString() + "시 " + minute.toString() + "분"
            else {
                "오후 " + (hour - 12).toString() + "시 " + minute.toString() + "분"
            }
            tvReaminHour.text = remainhour.toString()

//            1시간 이상 남았을때 -> 시간 표시
            if (hourTime>=1 && !(imgProfile.isVisible)) {
                tvReaminHour.text = remainhour.toString()
                tvRemain.text = "시간 전"
            }
//            1시간 미만 남았을때 -> 분 표시
            else if (hourTime<1 && !(imgProfile.isVisible)){
                tvReaminHour.text = minTime.toString()
                tvRemain.text = "분 전"
            }

            imgCheck.setOnClickListener {
                imgCheck.setBackgroundResource(R.drawable.ic_todo_check_complete)
                imgCheck.visibility = View.INVISIBLE
                imgProfile.visibility = View.VISIBLE
                imguncheckBackground.visibility = View.INVISIBLE
                imgTimer.setImageResource(R.drawable.ic_todo_complete)
                tvReaminHour.visibility = View.INVISIBLE
                tvRemain.text = "유리"
            }

//         콕 찌르기 -> 현재 시간이 정해진 시간보다 지났을때
            if (current.time > cal.time){
                imgCheck.visibility = View.INVISIBLE
                imgProfile.visibility = View.INVISIBLE
                imguncheckBackground.visibility = View.INVISIBLE
                imgTimer.visibility = View.INVISIBLE
                tvReaminHour.visibility = View.INVISIBLE
                tvRemain.visibility = View.INVISIBLE

                layoutCock.visibility = View.VISIBLE
            }
            layoutCock.setOnClickListener {
                val todocockdialog = TodoCockDialogFragment()
                todocockdialog.show(fragmentManager, "todocockdialog")
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TodayTodoAdapter.ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_todo_items, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodayTodoAdapter.ItemViewHolder, position: Int) {
        holder.bind(todoList[position], context, fragmentManager)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }
}