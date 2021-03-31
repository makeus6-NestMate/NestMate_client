package com.nestmate.nm1.src.main.home.nest.todo

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nestmate.nm1.R
import com.nestmate.nm1.src.main.home.nest.todo.model.TodayTodo
import kotlinx.android.synthetic.main.layout_todo_items.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.floor

class TodayTodoAdapter(val context: Context, private val todoList: MutableList<TodayTodo>, private val fragmentManager:FragmentManager) :
    RecyclerView.Adapter<TodayTodoAdapter.ItemViewHolder>() {
    private var listener: OnItemClickListener? = null

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle = itemView.findViewById<TextView>(R.id.todo_tv_title)
        private val tvTime = itemView.findViewById<TextView>(R.id.todo_tv_time)
        private val tvTimeOrName = itemView.findViewById<TextView>(R.id.todo_tv_timeorname)
        private val imgTimer = itemView.findViewById<ImageView>(R.id.todo_img_timer)
        private val imgCheck = itemView.findViewById<ImageView>(R.id.todo_img_check)
        private val imgProfile = itemView.findViewById<ImageView>(R.id.todo_img_profile)
        private val imguncheckBackground = itemView.findViewById<ImageView>(R.id.todo_img_notcheckbackground)
        private val layoutCock = itemView.findViewById<LinearLayout>(R.id.todo_layout_cock)

        fun bind(todayTodo: TodayTodo, context: Context, fragmentManager: FragmentManager) {
            val bundle = Bundle()

//           설정된 시간
            val cal = Calendar.getInstance()
            val formatData = SimpleDateFormat("yyyy/MM/dd/hh/mm", Locale.getDefault()).parse(todayTodo.deadline)
            cal.time = formatData
            val hour = cal.get(Calendar.HOUR_OF_DAY)
            val minute = cal.get(Calendar.MINUTE)

//            Log.d("hello", "$hour/$minute")

//            현재 시간
            val current = Calendar.getInstance()
//            Log.d("hello", current.get(Calendar.HOUR_OF_DAY).toString()+"/"+current.get(Calendar.MINUTE).toString())

            val remainhour = hour-current.get(Calendar.HOUR_OF_DAY) //시간만뺀거
            val diffSec = (cal.timeInMillis - current.timeInMillis) / 1000
            val hourTime = floor((diffSec / 3600).toDouble()).toInt() //남은시간
            val minTime = floor(((diffSec - 3600 * hourTime) / 60).toDouble()).toInt() //남은 분

            tvTitle.text = todayTodo.todo
            tvTime.text = if (hour < 12)
                "오전 " + hour.toString() + "시 " + minute.toString() + "분"
            else {
                "오후 " + (hour - 12).toString() + "시 " + minute.toString() + "분"
            }

//            1시간 이상 남았을때 -> 시간 표시
            if (hourTime>=1 && todayTodo.profileImg.isNullOrEmpty()) {
                tvTimeOrName.text = remainhour.toString()+"시간 전"
            }
//            1시간 미만 남았을때 -> 분 표시
            else if (hourTime<1 && todayTodo.profileImg.isNullOrEmpty()){
                tvTimeOrName.text = minTime.toString()+"분 전"
            }

            if (todayTodo.profileImg.isNotEmpty()){ //프로필이 비지 않았음 -> 완료
                imgCheck.visibility = View.INVISIBLE //체크 안보임
                imgProfile.visibility = View.VISIBLE //프로필 보임
                tvTimeOrName.visibility = View.VISIBLE //닉네임 보임
                imguncheckBackground.visibility = View.INVISIBLE //체크 안보임
                imgTimer.setImageResource(R.drawable.ic_todo_complete) //완료 표시
                imgTimer.visibility = View.VISIBLE
                layoutCock.visibility = View.INVISIBLE //콕찌르기 안보여야함
                layoutCock.isEnabled = false //콕찌르기 클릭 무력화
                imgCheck.isEnabled = false //이미지 체크 무력화

                Glide
                    .with(context)
                    .load(todayTodo.profileImg)
                    .into(imgProfile) //멤버 프로필

                tvTimeOrName.text = todayTodo.nickname //멤버 닉네임
            } else if (todayTodo.profileImg.isNullOrEmpty() && current.time.after(cal.time)){ //해결하지 않았는데 시간이 지난경우
                imgCheck.visibility = View.INVISIBLE //체크안보임
                imgProfile.visibility = View.INVISIBLE //프로필 안보임
                imguncheckBackground.visibility = View.INVISIBLE //체크 배경 안보임
                imgTimer.visibility = View.INVISIBLE //아이콘 안보임
                tvTimeOrName.visibility = View.INVISIBLE //시간 안보임

                layoutCock.visibility = View.VISIBLE //콕 보임
                layoutCock.isEnabled = true
                layoutCock.setOnClickListener {
                    bundle.putInt("todoId", todayTodo.todoId)
                    val todocockdialog = TodoCockDialogFragment()

                    todocockdialog.arguments = bundle //todoId전달
                    todocockdialog.show(fragmentManager, "todocockdialog")
                }
            }

//         콕 찌르기 -> 현재 시간이 정해진 시간보다 지났을때
            Log.d("시간", current.time.toString())
            Log.d("시간", cal.time.toString())
            Log.d("시간/비교", current.time.after(cal.time).toString())
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

        holder.itemView.todo_img_check.setOnClickListener {
            listener!!.onClicked(position, todoList[position].todoId)
        }
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    fun setOnClickListener(listener: OnItemClickListener){
        this.listener = listener
    }

    interface OnItemClickListener{
        fun onClicked(position: Int, todoId: Int)
    }
}