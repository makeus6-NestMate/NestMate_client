package com.example.nm1.src.main.home.nest.todo

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nm1.R
import com.example.nm1.config.ApplicationClass
import com.example.nm1.src.main.home.nest.todo.model.*
import com.example.nm1.util.LoadingDialog
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.floor

class TodayTodoAdapter(val context: Context, private val todoList: List<TodayTodo>, private val fragmentManager:FragmentManager) :
    RecyclerView.Adapter<TodayTodoAdapter.ItemViewHolder>(), TodoView {
    private lateinit var mLoadingDialog: LoadingDialog
    private lateinit var imageViewProfile:ImageView

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

        private val roomId = ApplicationClass.sSharedPreferences.getInt("roomId", 0)


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
//                tvRemain.text = "유리"

                showLoadingDialog(context)
                TodoService(this@TodayTodoAdapter).tryPostCompleteTodo(roomId, todayTodo.todoId)
            }

//         콕 찌르기 -> 현재 시간이 정해진 시간보다 지났을때
            Log.d("시간", current.time.toString())
            Log.d("시간", cal.time.toString())
            Log.d("시간/비교", current.time.after(cal.time).toString())

            if (current.time.after(cal.time)){
                imgCheck.visibility = View.INVISIBLE
                imgProfile.visibility = View.INVISIBLE
                imguncheckBackground.visibility = View.INVISIBLE
                imgTimer.visibility = View.INVISIBLE
                tvReaminHour.visibility = View.INVISIBLE
                tvRemain.visibility = View.INVISIBLE

                layoutCock.visibility = View.VISIBLE
            }

            layoutCock.setOnClickListener {
                bundle.putInt("todoId", todayTodo.todoId)
                val todocockdialog = TodoCockDialogFragment()

                todocockdialog.arguments = bundle //todoId전달
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

    private fun showLoadingDialog(context: Context) {
        mLoadingDialog = LoadingDialog(context)
        mLoadingDialog.show()
    }

    private fun dismissLoadingDialog() {
        if (mLoadingDialog.isShowing) {
            mLoadingDialog.dismiss()
        }
    }

    override fun onAddOneDayTodoSuccess(response: AddOneDayTodoResponse) {
        TODO("Not yet implemented")
    }

    override fun onAddOneDayTodoFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onAddRepeatTodoSuccess(response: AddRepeatTodoResponse) {
        TODO("Not yet implemented")
    }

    override fun onAddRepeatTodoFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetOneDayTodoSuccess(response: GetOneDayTodoResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetOneDayTodoFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetRepeatTodoSuccess(response: GetRepeatTodoResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetRepeatTodoFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetTodayTodoSuccess(response: GetTodayTodoResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetTodayTodoFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onPostCompleteTodoSuccess(response: PostTodoCompleteResponse) {
        dismissLoadingDialog()

    }

    override fun onPostCompleteTodoFailure(message: String) {
        dismissLoadingDialog()
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onGetCockMemberSuccess(response: GetCockMemberResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetCockMemberFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onPostCockSuccess(response: PostCockResponse) {
        TODO("Not yet implemented")
    }

    override fun onPostCockFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onPutOneDayTodoSuccess(response: PutOneDayTodoResponse) {
        TODO("Not yet implemented")
    }

    override fun onPutOneDayTodoFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onPutRepeatTodoSuccess(response: PutRepeatTodoResponse) {
        TODO("Not yet implemented")
    }

    override fun onPutRepeatTodoFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteOneDayTodoSuccess(response: DeleteOneDayTodoResponse) {
        TODO("Not yet implemented")
    }

    override fun onDeleteOneDayTodoFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteRepeatTodoSuccess(response: DeleteRepeatTodoResponse) {
        TODO("Not yet implemented")
    }

    override fun onDeleteRepeatTodoFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetSearchOneDayTodoSuccess(response: GetSearchOneDayTodoResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetSearchOneDayTodoFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetSearchRepeatTodoSuccess(response: GetSearchRepeatTodoResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetSearchRepeatTodoFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetSearchTodoByDateSuccess(response: GetSearchTodoByDateResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetSearchTodoByDateFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteAllOneDayTodoSuccess(response: DeleteAllOneDayTodoResponse) {
        TODO("Not yet implemented")
    }

    override fun onDeleteAllOneDayTodoFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteAllRepeatTodoSuccess(response: DeleteAllRepeatTodoResponse) {
        TODO("Not yet implemented")
    }

    override fun onDeleteAllRepeatTodoFailure(message: String) {
        TODO("Not yet implemented")
    }
}