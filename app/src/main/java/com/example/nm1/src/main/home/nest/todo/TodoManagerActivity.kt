package com.example.nm1.src.main.home.nest.todo

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.nm1.R
import com.example.nm1.config.ApplicationClass
import com.example.nm1.config.BaseActivity
import com.example.nm1.databinding.ActivityTodoManagerBinding
import com.example.nm1.src.main.home.nest.todo.model.*

class TodoManagerActivity : BaseActivity<ActivityTodoManagerBinding>(ActivityTodoManagerBinding::inflate), TodoView {
    private val selectedDays = Array(7){0}
    lateinit var onedayadapter:TodoOneDayManagerAdapter
    lateinit var repeatadapter:TodoRepeatManagerAdapter
    private val isrepeat = Array(2){false}
    private val roomId = ApplicationClass.sSharedPreferences.getInt("roomId", 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isrepeat[0] = true

 //       리프레시 레이아웃
        binding.todoManagerRefreshlayout.setOnRefreshListener {
            if (isrepeat[1])
                TodoService(this).tryGetOneDayTodo(roomId)

            // 새로고침 완료시,
            // 새로고침 아이콘이 사라질 수 있게 isRefreshing = false
            binding.todoManagerRefreshlayout.isRefreshing = false
        }

//       반복
        binding.todoManagerBtnRepeat.setOnClickListener {
            binding.todoManagerBtnRepeat.setBackgroundResource(R.drawable.orange_button)
            binding.todoManagerBtnRepeat.setTextColor(
                ContextCompat.getColor(this, R.color.white
                )
            )
            binding.todoManagerBtnOne.setBackgroundResource(R.drawable.white_button)
            binding.todoManagerBtnOne.setTextColor(
                ContextCompat.getColor(
                    this, R.color.gray2
                )
            )
            isrepeat[0] = true
            isrepeat[1] = false
        }

//        하루만
        binding.todoManagerBtnOne.setOnClickListener {
            binding.todoManagerBtnOne.setBackgroundResource(R.drawable.orange_button)
            binding.todoManagerBtnOne.setTextColor(
                ContextCompat.getColor(
                    this, R.color.white
                )
            )
            binding.todoManagerBtnRepeat.setBackgroundResource(R.drawable.white_button)
            binding.todoManagerBtnRepeat.setTextColor(
                ContextCompat.getColor(
                    this, R.color.gray2
                )
            )
            isrepeat[0] = false
            isrepeat[1] = true

            showLoadingDialog(this)
            TodoService(this).tryGetOneDayTodo(roomId)
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
        dismissLoadingDialog()
        onedayadapter = TodoOneDayManagerAdapter(this, response.result.todo, supportFragmentManager)
        binding.todoManagerRecyclerview.adapter = onedayadapter
        onedayadapter.notifyDataSetChanged()
    }

    override fun onGetOneDayTodoFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onGetRepeatTodoSuccess(response: GetRepeatTodoResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetRepeatTodoFailure(message: String) {
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
}