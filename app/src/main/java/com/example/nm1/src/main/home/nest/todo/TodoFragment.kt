package com.example.nm1.src.main.home.nest.todo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import com.example.nm1.R
import com.example.nm1.config.BaseFragment
import com.example.nm1.databinding.FragmentTodoBinding
import com.example.nm1.src.main.home.HomeService
import com.example.nm1.src.main.home.nest.todo.models.AddOneDayTodoResponse
import com.example.nm1.src.main.home.nest.todo.models.AddRepeatTodoResponse
import java.text.SimpleDateFormat
import java.util.*

class TodoFragment : BaseFragment<FragmentTodoBinding>(
    FragmentTodoBinding::bind,
    R.layout.fragment_todo
), TodoFragmentView {

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val todolist = arrayListOf<Todo>()
        val todoadapter = TodayTodoAdapter(requireContext(), todolist, parentFragmentManager)
        binding.todoRecycler.adapter = todoadapter

        //    리프레시 레이아웃
        binding.todoRefreshlayout.setOnRefreshListener {
            todoadapter.notifyDataSetChanged()
            // 새로고침 완료시,
            // 새로고침 아이콘이 사라질 수 있게 isRefreshing = false
            binding.todoRefreshlayout.isRefreshing = false
        }

//       오늘날짜 설정
        val todayDate = Calendar.getInstance().time
        val todayMonth: String = SimpleDateFormat("MM", Locale.KOREAN).format(todayDate)
        val todayDay: String = SimpleDateFormat("dd", Locale.KOREAN).format(todayDate)
        val todayYoil: String = SimpleDateFormat("E", Locale.KOREAN).format(todayDate)

        val todayString = todayMonth+"월 "+todayDay+"일 "+"("+todayYoil+")"
        binding.todoTvTodaydate.text = todayString

//       할일추가 dialog 띄우기
        val tododialog = TodoAddDialogFragment()
        binding.todoBtnAddtodo.setOnClickListener {
            tododialog.show(parentFragmentManager, "tododialog")
        }

//        할일 관리
        binding.todoBtnManager.setOnClickListener {
            startActivity(Intent(requireContext(), TodoManagerActivity::class.java))
        }

//      dialog에서 받은 것을 recylerview에 추가
        // 요청키이름은 마치 onActivityResult 에서 사용하는 requestKey 같은 개념입니다.
        // 해당 요청키로 전달된 값을 처리하겠다는 의미 입니다.

        setFragmentResultListener("todoadd_one") { _, bundle ->
            binding.todoLayoutEmpty.visibility = View.INVISIBLE
            binding.todoRecycler.visibility = View.VISIBLE //목록이 뜨게
            bundle.getString("todoadd_one_ok")?.let {
                if (it=="ok"){
                    showLoadingDialog(requireContext())
                    //TodoService(this)
                }
            }
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
}