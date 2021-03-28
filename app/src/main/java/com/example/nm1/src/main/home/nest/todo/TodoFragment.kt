package com.example.nm1.src.main.home.nest.todo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.nm1.R
import com.example.nm1.config.ApplicationClass
import com.example.nm1.config.BaseFragment
import com.example.nm1.databinding.FragmentTodoBinding
import com.example.nm1.src.main.home.nest.todo.model.*
import java.text.SimpleDateFormat
import java.util.*

class TodoFragment : BaseFragment<FragmentTodoBinding>(
    FragmentTodoBinding::bind,
    R.layout.fragment_todo
), TodoView {
    private val roomId = ApplicationClass.sSharedPreferences.getInt("roomId", 0)
    private lateinit var adapter: TodayTodoAdapter

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //    리프레시 레이아웃
        binding.todoRefreshlayout.setOnRefreshListener {
            // 새로고침 완료시,
            // 새로고침 아이콘이 사라질 수 있게 isRefreshing = false
            showLoadingDialog(requireContext())
            TodoService(this).tryGetTodayTodo(roomId)
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
        val tododialog = TodoAddDialog()
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

//        setFragmentResultListener("todoadd_one") { _, bundle ->
//            binding.todoLayoutEmpty.visibility = View.INVISIBLE
//            binding.todoRecycler.visibility = View.VISIBLE //목록이 뜨게
//            bundle.getString("todoadd_one_ok")?.let {
//                if (it=="ok"){
//                    //showLoadingDialog(requireContext())
//                    //TodoService(this)
//                }
//            }
//        }
    }

    private val onClicked = object: TodayTodoAdapter.OnItemClickListener{
        override fun onClicked(position: Int, todoId: Int) {
            showLoadingDialog(requireContext())
            TodoService(this@TodoFragment).tryPostCompleteTodo(roomId, todoId)
        }
    }

    override fun onResume() {
        super.onResume()
        showLoadingDialog(requireContext())
        TodoService(this).tryGetTodayTodo(roomId)
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
        dismissLoadingDialog()
        val todayadapter = TodayTodoAdapter(requireContext(), response.result.todo, parentFragmentManager)

        if (response.result.todo.isNotEmpty()){ //오늘 할일이 있으면
            binding.todoLayoutEmpty.visibility = View.INVISIBLE
            binding.todoRecycler.visibility = View.VISIBLE

            binding.todoRecycler.adapter = todayadapter
            todayadapter.notifyDataSetChanged()
        }

        this.adapter = todayadapter
        this.adapter.setOnClickListener(onClicked)
    }

    override fun onGetTodayTodoFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onPostCompleteTodoSuccess(response: PostTodoCompleteResponse) {
        dismissLoadingDialog()
    }

    override fun onPostCompleteTodoFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onPostCockSuccess(response: PostCockResponse) {
        TODO("Not yet implemented")
    }

    override fun onPostCockFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetCockMemberSuccess(response: GetCockMemberResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetCockMemberFailure(message: String) {
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