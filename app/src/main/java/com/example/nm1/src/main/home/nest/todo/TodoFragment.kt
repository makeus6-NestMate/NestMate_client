package com.example.nm1.src.main.home.nest.todo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.RecyclerView
import com.example.nm1.R
import com.example.nm1.config.ApplicationClass
import com.example.nm1.config.BaseFragment
import com.example.nm1.databinding.FragmentTodoBinding
import com.example.nm1.src.main.home.HomeService
import com.example.nm1.src.main.home.nest.todo.model.*
import java.text.SimpleDateFormat
import java.util.*

class TodoFragment : BaseFragment<FragmentTodoBinding>(
    FragmentTodoBinding::bind,
    R.layout.fragment_todo
), TodoView {
    private var page = 0
    private var todaylist = mutableListOf<TodayTodo>()
    var istodoend = false
    var todoAdapter: TodayTodoAdapter?=null
    var itemposition:Int?=null

    private val roomId = ApplicationClass.sSharedPreferences.getInt("roomId", 0)
    private var adapter: TodayTodoAdapter?= null

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        page = 0
        todaylist.clear()

        todoAdapter = TodayTodoAdapter(requireContext(), todaylist, parentFragmentManager)

        showLoadingDialog(requireContext())
        TodoService(this).tryGetTodayTodo(roomId, page)

        //    리프레시 레이아웃
        binding.todoRefreshlayout.setOnRefreshListener {
            // 새로고침 완료시,
            // 새로고침 아이콘이 사라질 수 있게 isRefreshing = false
            page = 0
            istodoend = false
            todaylist.clear()

            showLoadingDialog(requireContext())
            TodoService(this).tryGetTodayTodo(roomId, page)
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

        binding.todoRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

//             direction:  양수일경우엔 아래 스크롤, 음수일경우엔 위 스크롤
//                수직으로 더이상 스크롤이 안되면, 데이터를 더해서 불러옴
                if (!binding.todoRecycler.canScrollVertically(1)){
                    if (!istodoend) {
                        dismissLoadingDialog()
                        showLoadingDialog(requireContext())

                        TodoService(this@TodoFragment).tryGetTodayTodo(roomId, ++page)
                    }
                }
            }
        })

        showLoadingDialog(requireContext())
        TodoService(this).tryGetTodayTodo(roomId, page)

//      dialog에서 받은 것을 recylerview에 추가
        // 요청키이름은 마치 onActivityResult 에서 사용하는 requestKey 같은 개념입니다.
        // 해당 요청키로 전달된 값을 처리하겠다는 의미 입니다.

        setFragmentResultListener("todoadd") { _, bundle ->
            binding.todoLayoutEmpty.visibility = View.INVISIBLE
            binding.todoRecycler.visibility = View.VISIBLE //목록이 뜨게
            bundle.getString("todoadd_ok")?.let {
                if (it=="ok"){
                    page = 0
                    todaylist.clear()
                    istodoend = false
                    showLoadingDialog(requireContext())
                    TodoService(this).tryGetTodayTodo(roomId, page)
                }
            }
        }

        setFragmentResultListener("todoedit") { _, bundle ->
            binding.todoLayoutEmpty.visibility = View.INVISIBLE
            binding.todoRecycler.visibility = View.VISIBLE //목록이 뜨게
            bundle.getString("todoedit_ok")?.let {
                if (it=="ok"){
                    page = 0
                    todaylist.clear()
                    istodoend = false
                    showLoadingDialog(requireContext())
                    TodoService(this).tryGetTodayTodo(roomId, page)
                }
            }
        }
    }

    private val onClicked = object: TodayTodoAdapter.OnItemClickListener{
        override fun onClicked(position: Int, todoId: Int) {
            showLoadingDialog(requireContext())
            TodoService(this@TodoFragment).tryPostCompleteTodo(roomId, todoId)
            itemposition = position

            todoAdapter?.notifyItemChanged(position)
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
        dismissLoadingDialog()
//      맨 처음(page=0) -> 검색결과가 하나라도 있으면
        if (page==0 && response.result.todo.isNotEmpty()){
//            Log.d("둥지", "둥지있음")
            binding.todoLayoutEmpty.visibility = View.INVISIBLE
            binding.todoRecycler.visibility = View.VISIBLE

            todaylist.addAll(response.result.todo)

            binding.todoRecycler.adapter = todoAdapter
        }
//      page=1부터 불러오고, 둥지가 있으면 추가해줘야함 ->
        else if (page!=0 && response.result.todo.isNotEmpty()){
//            Log.d("둥지", "둥지추가")
            todaylist.addAll(response.result.todo)
            todoAdapter?.notifyItemInserted(todaylist.size-1)
        }

//        페이지추가 끝
        if (page!=0 && response.result.todo.isNullOrEmpty()){
//            Log.d("둥지", "둥지끝")
            istodoend = true
        }

        this.adapter = todoAdapter!!
        this.adapter!!.setOnClickListener(onClicked)
    }

    override fun onGetTodayTodoFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onPostCompleteTodoSuccess(response: PostTodoCompleteResponse) {
        dismissLoadingDialog()
        todoAdapter?.notifyItemChanged(itemposition!!)
        showCustomToast("할일 완료!")
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