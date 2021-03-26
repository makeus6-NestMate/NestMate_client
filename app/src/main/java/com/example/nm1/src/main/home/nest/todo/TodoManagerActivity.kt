package com.example.nm1.src.main.home.nest.todo

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import com.example.nm1.R
import com.example.nm1.config.ApplicationClass
import com.example.nm1.config.BaseActivity
import com.example.nm1.databinding.ActivityTodoManagerBinding
import com.example.nm1.src.main.home.nest.todo.model.*
import java.util.*
import kotlin.properties.Delegates

class TodoManagerActivity : BaseActivity<ActivityTodoManagerBinding>(ActivityTodoManagerBinding::inflate), TodoView {
    private lateinit var onedayadapter:TodoOneDayManagerAdapter
    private lateinit var repeatadapter:TodoRepeatManagerAdapter
    private val isrepeat = Array(2){false}
    private val roomId = ApplicationClass.sSharedPreferences.getInt("roomId", 0)
    private var selectedyear by Delegates.notNull<Int>()
    private var selectedmonth by Delegates.notNull<Int>()
    private var selectedday by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isrepeat[0] = true
        showLoadingDialog(this)
        TodoService(this).tryGetRepeatTodo(roomId)

        //       리프레시 레이아웃
        binding.todoManagerRefreshlayout.setOnRefreshListener {
            if (isrepeat[1]) //하루
                TodoService(this).tryGetOneDayTodo(roomId)
            else if (isrepeat[0]) //반복
                TodoService(this).tryGetRepeatTodo(roomId)

            // 새로고침 완료시, 새로고침 아이콘이 사라질 수 있게
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

            showLoadingDialog(this)
            TodoService(this).tryGetRepeatTodo(roomId)
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

//        검색창 띄우기
        binding.todoManagerBtnSearch.setOnClickListener {
            binding.todoManagerBtnSearch.visibility = View.INVISIBLE
            binding.todoManagerSearchExit.visibility = View.VISIBLE
            binding.todoManagerEdtSearch.visibility = View.VISIBLE
            if (isrepeat[1])  //캘린더 검색은 하루만일때 가능하므로
                binding.todoManagerBtnCalendar.visibility = View.VISIBLE
        }

//        검색창 닫기
        binding.todoManagerSearchExit.setOnClickListener {
            binding.todoManagerBtnSearch.visibility = View.VISIBLE
            binding.todoManagerSearchExit.visibility = View.INVISIBLE
            binding.todoManagerEdtSearch.visibility = View.INVISIBLE
            binding.todoManagerBtnCalendar.visibility = View.INVISIBLE
        }

        // 키워드 입력 후 엔터누르면 검색
        binding.todoManagerEdtSearch.setOnEditorActionListener { v, actionId, event ->
            when (actionId){
                EditorInfo.IME_ACTION_SEARCH -> {
//                   하루만
                    if (isrepeat[1]) {
                        showLoadingDialog(this)
                        TodoService(this).trySearchGetOneDayTodo(roomId, binding.todoManagerEdtSearch.text.toString().trim())
                    }
                    else if (isrepeat[0]){
                        showLoadingDialog(this)
                        TodoService(this).trySearchGetRepeatTodo(roomId, binding.todoManagerEdtSearch.text.toString().trim())
                    }
                }
            }
            true
        }

//      캘린더 검색 (날짜 검색)
        binding.todoManagerBtnCalendar.setOnClickListener {
            //            datepickerdialog에 표시할 달력
            val datepickercalendar = Calendar.getInstance()
            val year = datepickercalendar.get(Calendar.YEAR)
            val month = datepickercalendar.get(Calendar.MONTH)
            val day = datepickercalendar.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(
                this,
                R.style.MyDatePickerStyle,
                { _, year, monthOfYear, dayOfMonth ->
//                  월이 0부터 시작하여 1을 더해주어야함
                    val month = monthOfYear + 1
//                   선택한 날짜의 요일을 구하기 위한 calendar
                    val calendar = Calendar.getInstance()
//                    선택한 날짜 세팅
                    calendar.set(year, monthOfYear, dayOfMonth)

                    selectedyear = year
                    selectedmonth = month
                    selectedday = dayOfMonth

                    showLoadingDialog(this)
                    TodoService(this).trySearchTodoByDate(roomId, "$selectedyear/$selectedmonth/$selectedday")
                },
                year,
                month,
                day
            )
//           최소 날짜를 현재 시각 이후로 -> 어차피 시간지나면 없어짐
            dpd.datePicker.minDate = System.currentTimeMillis() - 1000
            dpd.show()
        }

//        전체 삭제
        binding.todoManagerTvDeleteall.setOnClickListener {
            if (isrepeat[0]){ //반복
                showLoadingDialog(this)
                TodoService(this).tryDeleteAllRepeatTodo(roomId)
            }
            else if (isrepeat[1]){ //하루
                showLoadingDialog(this)
                TodoService(this).tryDeleteAllOneDayTodo(roomId)
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
        dismissLoadingDialog()
        repeatadapter = TodoRepeatManagerAdapter(this, response.result.todo, supportFragmentManager)
        binding.todoManagerRecyclerview.adapter = repeatadapter
        repeatadapter.notifyDataSetChanged()
    }

    override fun onGetRepeatTodoFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onGetTodayTodoSuccess(response: GetTodayTodoResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetTodayTodoFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onPostCompleteTodoSuccess(response: PostTodoCompleteResponse) {
        TODO("Not yet implemented")
    }

    override fun onPostCompleteTodoFailure(message: String) {
        TODO("Not yet implemented")
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
        dismissLoadingDialog()
        if (response.code==494){
            showCustomToast(response.message!!)
        }
//      검색 결과가 있으면
        else if (response.result.todo.isNotEmpty()) {
            onedayadapter =
                TodoOneDayManagerAdapter(this, response.result.todo, supportFragmentManager)
            binding.todoManagerRecyclerview.adapter = onedayadapter
            onedayadapter.notifyDataSetChanged()
        }
//      없으면
        else {
            showCustomToast("검색 결과가 없습니다")
        }
    }

    override fun onGetSearchOneDayTodoFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onGetSearchRepeatTodoSuccess(response: GetSearchRepeatTodoResponse) {
        dismissLoadingDialog()
        if (response.code==494){ //검색어를 입력하지 않았을 경우
            showCustomToast(response.message!!)
        }
//      검색 결과가 있으면
        else if (response.result.todo.isNotEmpty()) {
            repeatadapter =
                TodoRepeatManagerAdapter(this, response.result.todo, supportFragmentManager)
            binding.todoManagerRecyclerview.adapter = repeatadapter
            repeatadapter.notifyDataSetChanged()
        }
//      없으면
        else {
            showCustomToast("검색 결과가 없습니다")
        }
    }

    override fun onGetSearchRepeatTodoFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onGetSearchTodoByDateSuccess(response: GetSearchTodoByDateResponse) {
        dismissLoadingDialog()
        //      검색 결과가 있으면
        if (response.result.todo.isNotEmpty()) {
            onedayadapter =
                TodoOneDayManagerAdapter(this, response.result.todo, supportFragmentManager)
            binding.todoManagerRecyclerview.adapter = onedayadapter
            onedayadapter.notifyDataSetChanged()
        }
//      없으면
        else {
            showCustomToast("검색 결과가 없습니다")
        }
    }

    override fun onGetSearchTodoByDateFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onDeleteAllOneDayTodoSuccess(response: DeleteAllOneDayTodoResponse) {
        dismissLoadingDialog()
        onedayadapter.notifyDataSetChanged()
        showCustomToast("하루만 할일이 모두 삭제되었습니다")
    }

    override fun onDeleteAllOneDayTodoFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onDeleteAllRepeatTodoSuccess(response: DeleteAllRepeatTodoResponse) {
        dismissLoadingDialog()
        repeatadapter.notifyDataSetChanged()
        showCustomToast("반복 할일이 모두 삭제되었습니다")
    }

    override fun onDeleteAllRepeatTodoFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }
}