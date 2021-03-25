package com.example.nm1.src.main.home.nest.todo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nm1.R
import com.example.nm1.src.main.home.nest.todo.model.RepeatTodo

class TodoRepeatManagerAdapter(val context: Context, private val repeattodolist: List<RepeatTodo>, private val fragmentManager: FragmentManager):
    RecyclerView.Adapter<TodoRepeatManagerAdapter.ItemViewHolder>(){

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvOneDayOrRepeat = itemView.findViewById<TextView>(R.id.todo_manager_tv_oneorrepeat)
        private val tvDay = itemView.findViewById<TextView>(R.id.todo_manager_tv_day)
        private val tvTitle = itemView.findViewById<TextView>(R.id.todo_manager_tv_title)
        private val tvTime = itemView.findViewById<TextView>(R.id.todo_manager_tv_time)
        private val btnTool = itemView.findViewById<Button>(R.id.todo_manager_tool)

        fun bind(repeatTodo: RepeatTodo, context: Context, fragmentManager: FragmentManager) {
            val bundle = Bundle() //bottomsheetdialog에 데이터를 전달하기 위함
            val days: MutableList<String> = ArrayList()

            tvOneDayOrRepeat.text = "반복"
            tvTitle.text = repeatTodo.todo
//           요일
            if (repeatTodo.day[0] == '1') {
                days.add("월")
            }
            if (repeatTodo.day[1] == '1') {
                days.add("화")
            }
            if (repeatTodo.day[2] == '1') {
                days.add("수")
            }
            if (repeatTodo.day[3] == '1') {
                days.add("목")
            }
            if (repeatTodo.day[4] == '1') {
                days.add("금")
            }
            if (repeatTodo.day[5] == '1') {
                days.add("토")
            }
            if (repeatTodo.day[6] == '1') {
                days.add("일")
            }
            tvDay.text = days.joinToString(".")
//           시간
            when {
//                오전 12시~11시
                repeatTodo.deadline.substring(0, 2).toInt() in 0..11 -> {
                    tvTime.text = "오전 "+repeatTodo.deadline.substring(0, 2)+"시 "+repeatTodo.deadline.substring(3, 5)+"분"
                }
//                오후 12시
                repeatTodo.deadline.substring(0, 2).toInt()==12 -> {
                    tvTime.text = "오후 12시 "+repeatTodo.deadline.substring(3, 5)+"분"
                }
//                오후 1시~11시
                repeatTodo.deadline.substring(0, 2).toInt() in 13..23 -> {
                    tvTime.text = "오후 "+(repeatTodo.deadline.substring(0, 2).toInt()-12)+"시 "+repeatTodo.deadline.substring(3, 5)+"분"
                }
            }
            btnTool.setOnClickListener {
                val todoManagerBottomSheet = TodoManagerBottomSheet()
                bundle.putInt("todoId", repeatTodo.todoId)
                bundle.putString("onedayorrepeat", "repeat") //반복만 데이터 삽입
                bundle.putString("time", repeatTodo.deadline)
                bundle.putString("days", repeatTodo.day)
                bundle.putString("todotitle", repeatTodo.todo)

//                수정삭제 bottomsheet
                todoManagerBottomSheet.arguments = bundle
                todoManagerBottomSheet.show(fragmentManager, todoManagerBottomSheet.tag)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TodoRepeatManagerAdapter.ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.todo_manager_items, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoRepeatManagerAdapter.ItemViewHolder, position: Int) {
        holder.bind(repeattodolist[position], context, fragmentManager)
    }

    override fun getItemCount(): Int {
        return repeattodolist.size
    }
}