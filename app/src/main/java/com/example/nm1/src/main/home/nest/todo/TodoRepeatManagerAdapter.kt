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
import com.example.nm1.src.main.home.nest.todo.model.OneDayTodo

class TodoRepeatManagerAdapter(val context: Context, private val onedaytodolist: List<OneDayTodo>, val fragmentManager: FragmentManager):
    RecyclerView.Adapter<TodoRepeatManagerAdapter.ItemViewHolder>(){

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvOneDayOrRepeat = itemView.findViewById<TextView>(R.id.todo_manager_tv_oneorrepeat)
        private val tvDay = itemView.findViewById<TextView>(R.id.todo_manager_tv_day)
        private val tvTitle = itemView.findViewById<TextView>(R.id.todo_manager_tv_title)
        private val tvTime = itemView.findViewById<TextView>(R.id.todo_manager_tv_time)
        private val btnTool = itemView.findViewById<Button>(R.id.todo_manager_tool)

        fun bind(onedaytodo: OneDayTodo, context: Context, fragmentManager: FragmentManager) {
            val bundle = Bundle() //bottomsheetdialog에 데이터를 전달하기 위함

            tvOneDayOrRepeat.text = "하루"
            tvTitle.text = onedaytodo.todo
            tvDay.text = onedaytodo.deadline.substring(0, 2)+"월"+onedaytodo.deadline.substring(3, 5)+"일"
            when {
//                오전 12시~11시
                onedaytodo.deadline.substring(6, 8).toInt() in 0..11 -> {
                    tvTime.text = "오전 "+onedaytodo.deadline.substring(6, 8)+"시 "+onedaytodo.deadline.substring(9, 11)+"분"
                }
//                오후 12시
                onedaytodo.deadline.substring(6, 8).toInt()==12 -> {
                    tvTime.text = "오후 12시 "+onedaytodo.deadline.substring(9, 11)+"분"
                }
//                오후 1시~11시
                onedaytodo.deadline.substring(6, 8).toInt() in 13..23 -> {
                    tvTime.text = "오후 "+(onedaytodo.deadline.substring(6, 8).toInt()-12)+"시 "+onedaytodo.deadline.substring(9, 11)+"분"
                }
            }
            btnTool.setOnClickListener {
                val todoOneDayBottomSheet = TodoManagerBottomSheet()
                bundle.putInt("todoId", onedaytodo.todoId)

//                수정삭제 bottomsheet
                todoOneDayBottomSheet.arguments = bundle
                todoOneDayBottomSheet.show(fragmentManager, todoOneDayBottomSheet.tag)
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
        holder.bind(onedaytodolist[position], context, fragmentManager)
    }

    override fun getItemCount(): Int {
        return onedaytodolist.size
    }
}