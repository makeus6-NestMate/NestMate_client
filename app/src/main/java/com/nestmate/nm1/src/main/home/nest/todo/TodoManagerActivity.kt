package com.nestmate.nm1.src.main.home.nest.todo

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nestmate.nm1.R
import com.nestmate.nm1.config.ApplicationClass
import com.nestmate.nm1.config.BaseActivity
import com.nestmate.nm1.databinding.ActivityTodoManagerBinding
import com.nestmate.nm1.src.main.home.nest.todo.model.*
import java.util.*
import kotlin.properties.Delegates

class TodoManagerActivity : BaseActivity<ActivityTodoManagerBinding>(ActivityTodoManagerBinding::inflate), TodoView {
    private var onedaylist = mutableListOf<OneDayTodo>()
    private var repeatlist = mutableListOf<RepeatTodo>()
    private var onedayisOwnerlist = mutableListOf<OneDayTodo>()
    private var repeatisOwnerlist = mutableListOf<RepeatTodo>()

    private lateinit var onedayadapter:TodoOneDayManagerAdapter
    private lateinit var repeatadapter:TodoRepeatManagerAdapter
    private val isrepeat = Array(2){false}
    private val roomId = ApplicationClass.sSharedPreferences.getInt("roomId", 0)
    private var selectedyear by Delegates.notNull<Int>()
    private var selectedmonth by Delegates.notNull<Int>()
    private var selectedday by Delegates.notNull<Int>()

    private var page = 0       // 현재 페이지
    var searchKeyword = ""
    var searchdate = ""
    var istodoend = false //끝 판단
    var iskeywordsearch = false  //키워드 검색결과인지 판단
    var isdatesearch = false //날짜 검색결과인지 판단

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isrepeat[0] = true

//       반복
        binding.todoManagerBtnRepeat.setOnClickListener {
            page = 0 //초기화
            iskeywordsearch = false
            isdatesearch = false
            istodoend = false
            onedaylist.clear()
            repeatlist.clear()

            binding.todoManagerBtnSearch.visibility = View.VISIBLE
            binding.todoManagerSearchExit.visibility = View.INVISIBLE
            binding.todoManagerEdtSearch.visibility = View.INVISIBLE
            binding.todoManagerBtnCalendar.visibility = View.INVISIBLE
            binding.todoManagerTitle.text = "할일 관리"

            binding.todoManagerEdtSearch.text.clear()

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
            TodoService(this).tryGetRepeatTodo(roomId, page)
        }

//        하루만
        binding.todoManagerBtnOne.setOnClickListener {
            page = 0
            istodoend = false
            iskeywordsearch = false
            isdatesearch = false
            onedaylist.clear()
            repeatlist.clear()

            binding.todoManagerBtnSearch.visibility = View.VISIBLE
            binding.todoManagerSearchExit.visibility = View.INVISIBLE
            binding.todoManagerEdtSearch.visibility = View.INVISIBLE
            binding.todoManagerBtnCalendar.visibility = View.INVISIBLE
            binding.todoManagerTitle.text = "할일 관리"

            binding.todoManagerEdtSearch.text.clear()

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
            TodoService(this).tryGetOneDayTodo(roomId, page)
        }

        binding.todoManagerRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

//             direction:  양수일경우엔 아래 스크롤, 음수일경우엔 위 스크롤
//                수직으로 더이상 스크롤이 안되면, 데이터를 더해서 불러옴
                if (!binding.todoManagerRecyclerview.canScrollVertically(1)){
                    if (!istodoend) {
                        dismissLoadingDialog()
                        showLoadingDialog(this@TodoManagerActivity)

                        if (isrepeat[1] && !iskeywordsearch && !isdatesearch) //하루
                            TodoService(this@TodoManagerActivity).tryGetOneDayTodo(roomId, ++page)
                        else if (isrepeat[0] && !iskeywordsearch && !isdatesearch) //반복
                            TodoService(this@TodoManagerActivity).tryGetRepeatTodo(roomId, ++page)
                        else if (isrepeat[1] && iskeywordsearch && !isdatesearch) //하루 키워드 검색
                            TodoService(this@TodoManagerActivity).trySearchGetOneDayTodo(roomId, searchKeyword, ++page)
                        else if (isrepeat[0] && iskeywordsearch && !isdatesearch) //반복 키워드 검색
                            TodoService(this@TodoManagerActivity).trySearchGetRepeatTodo(roomId, searchKeyword, ++page)
                        else if (isrepeat[1] && !iskeywordsearch && isdatesearch) //하루 날짜 검색
                            TodoService(this@TodoManagerActivity).trySearchTodoByDate(roomId, searchdate, ++page)
                    }
                }
            }
        })

//        검색창 띄우기
        binding.todoManagerBtnSearch.setOnClickListener {
            binding.todoManagerBtnSearch.visibility = View.INVISIBLE
            binding.todoManagerSearchExit.visibility = View.VISIBLE
            binding.todoManagerEdtSearch.visibility = View.VISIBLE
            binding.todoManagerTitle.text = ""

            if (isrepeat[1])  //캘린더 검색은 하루만일때 가능하므로
                binding.todoManagerBtnCalendar.visibility = View.VISIBLE
        }

//        검색창 닫기
        binding.todoManagerSearchExit.setOnClickListener {
            binding.todoManagerBtnSearch.visibility = View.VISIBLE
            binding.todoManagerSearchExit.visibility = View.INVISIBLE
            binding.todoManagerEdtSearch.visibility = View.INVISIBLE
            binding.todoManagerBtnCalendar.visibility = View.INVISIBLE
            binding.todoManagerTitle.text = "할일 관리"

            binding.todoManagerEdtSearch.text.clear()
        }

        // 키워드 입력 후 엔터누르면 검색
        binding.todoManagerEdtSearch.setOnEditorActionListener { _, actionId, _ ->
            page = 0

            when (actionId){
                EditorInfo.IME_ACTION_SEARCH -> {
                    iskeywordsearch = true
                    searchKeyword = binding.todoManagerEdtSearch.text.toString().trim()
//                   하루만
                    if (isrepeat[1]) {
                        onedaylist.clear()
                        showLoadingDialog(this)
                        TodoService(this).trySearchGetOneDayTodo(roomId, searchKeyword, page)
                    }
                    else if (isrepeat[0]){
                        repeatlist.clear()
                        showLoadingDialog(this)
                        TodoService(this).trySearchGetRepeatTodo(roomId, searchKeyword, page)
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
                    isdatesearch = true
                    onedaylist.clear()

//                  월이 0부터 시작하여 1을 더해주어야함
                    val month = monthOfYear + 1
//                   선택한 날짜의 요일을 구하기 위한 calendar
                    val calendar = Calendar.getInstance()
//                    선택한 날짜 세팅
                    calendar.set(year, monthOfYear, dayOfMonth)

                    selectedyear = year
                    selectedmonth = month
                    selectedday = dayOfMonth

                    searchdate = "$selectedyear/$selectedmonth/$selectedday"

                    showLoadingDialog(this)
                    TodoService(this).trySearchTodoByDate(roomId, searchdate, page)
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

        onedayisOwnerlist.clear()
        for (i in response.result.todo){
            onedayisOwnerlist.add(i)
        }
        if (page==0 && response.result.todo.isNullOrEmpty()){
            onedaylist.clear()
            onedayadapter = TodoOneDayManagerAdapter(this, onedaylist, null, supportFragmentManager)
            binding.todoManagerRecyclerview.adapter = onedayadapter
        }
//      맨 처음(page=0) -> 검색결과가 하나라도 있으면
        else if (page==0 && response.result.todo.isNotEmpty()){
//            Log.d("둥지", "둥지있음")

            onedaylist.addAll(response.result.todo)
            onedayadapter = TodoOneDayManagerAdapter(this, onedaylist, null, supportFragmentManager)
            binding.todoManagerRecyclerview.adapter = onedayadapter
        }
//      page=1부터 불러오고, 둥지가 있으면 추가해줘야함 ->
        else if (page!=0 && response.result.todo.isNotEmpty()){
//            Log.d("둥지", "둥지추가")
            onedaylist.addAll(response.result.todo)
            onedayadapter.notifyItemInserted(onedaylist.size-1)
        }

//        페이지추가 끝
        if (page!=0 && response.result.todo.isNullOrEmpty()){
//            Log.d("둥지", "둥지끝")
            istodoend = true
        }
    }

    override fun onGetOneDayTodoFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onGetRepeatTodoSuccess(response: GetRepeatTodoResponse) {
        dismissLoadingDialog()
        repeatisOwnerlist.clear()
        for (i in response.result.todo){
            repeatisOwnerlist.add(i)
        }

        if (page==0 && response.result.todo.isNullOrEmpty()){
            repeatlist.clear()
            repeatadapter = TodoRepeatManagerAdapter(this, repeatlist, null, supportFragmentManager)
            binding.todoManagerRecyclerview.adapter = repeatadapter
        }
//      맨 처음(page=0) -> 검색결과가 하나라도 있으면
        else if (page==0 && response.result.todo.isNotEmpty()){
//            Log.d("둥지", "둥지있음")

            repeatlist.addAll(response.result.todo)
            repeatadapter = TodoRepeatManagerAdapter(this, repeatlist, null, supportFragmentManager)
            binding.todoManagerRecyclerview.adapter = repeatadapter
        }
//      page=1부터 불러오고, 둥지가 있으면 추가해줘야함 ->
        else if (page!=0 && response.result.todo.isNotEmpty()){
//            Log.d("둥지", "둥지추가")
            repeatlist.addAll(response.result.todo)
            repeatadapter.notifyItemInserted(repeatlist.size-1)
        }

//        페이지추가 끝
        if (page!=0 && response.result.todo.isNullOrEmpty()){
//            Log.d("둥지", "둥지끝")
            istodoend = true
        }
    }

    override fun onGetRepeatTodoFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onResume() {
        super.onResume()

        page = 0
        onedaylist.clear()
        repeatlist.clear()

        showLoadingDialog(this)
        if (isrepeat[1] && !iskeywordsearch && !isdatesearch) //하루
            TodoService(this).tryGetOneDayTodo(roomId, page)
        else if (isrepeat[0] && !iskeywordsearch && !isdatesearch) //반복
            TodoService(this).tryGetRepeatTodo(roomId, page)
        else if (isrepeat[1] && iskeywordsearch && !isdatesearch) //하루 키워드 검색
            TodoService(this).trySearchGetOneDayTodo(roomId, searchKeyword, page)
        else if (isrepeat[0] && iskeywordsearch && !isdatesearch) //반복 키워드 검색
            TodoService(this).trySearchGetRepeatTodo(roomId, searchKeyword, page)
        else if (isrepeat[1] && !iskeywordsearch && isdatesearch) //하루 날짜 검색
            TodoService(this).trySearchGetRepeatTodo(roomId, searchdate, page)
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
        else if (page==0 && response.result.todo.isNullOrEmpty()){
            showCustomToast("검색 결과가 없습니다")
        }
//      맨 처음(page=0) -> 검색결과가 하나라도 있으면
        else if (page==0 && response.result.todo.isNotEmpty()){
//            Log.d("둥지", "둥지있음")

            onedaylist.addAll(response.result.todo)
            onedayadapter = TodoOneDayManagerAdapter(this, onedaylist, onedayisOwnerlist, supportFragmentManager)
            binding.todoManagerRecyclerview.adapter = onedayadapter
        }
//      page=1부터 불러오고, 둥지가 있으면 추가해줘야함 ->
        else if (page!=0 && response.result.todo.isNotEmpty()){
//            Log.d("둥지", "둥지추가")
            onedaylist.addAll(response.result.todo)
            onedayadapter.notifyItemInserted(onedaylist.size-1)
        }

//        페이지추가 끝
        if (page!=0 && response.result.todo.isNullOrEmpty()){
//            Log.d("둥지", "둥지끝")
            istodoend = true
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
            else if (page==0 && response.result.todo.isNullOrEmpty()){
            showCustomToast("검색 결과가 없습니다")
        }
//      맨 처음(page=0) -> 검색결과가 하나라도 있으면
        else if (page==0 && response.result.todo.isNotEmpty()){
//            Log.d("둥지", "둥지있음")

            repeatlist.addAll(response.result.todo)
            repeatadapter = TodoRepeatManagerAdapter(this, repeatlist, repeatisOwnerlist, supportFragmentManager)
            binding.todoManagerRecyclerview.adapter = repeatadapter
        }
//      page=1부터 불러오고, 둥지가 있으면 추가해줘야함 ->
        else if (page!=0 && response.result.todo.isNotEmpty()){
//            Log.d("둥지", "둥지추가")
            repeatlist.addAll(response.result.todo)
            repeatadapter.notifyItemInserted(repeatlist.size-1)
        }

//        페이지추가 끝
        if (page!=0 && response.result.todo.isNullOrEmpty()){
//            Log.d("둥지", "둥지끝")
            istodoend = true
        }
    }

    override fun onGetSearchRepeatTodoFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onGetSearchTodoByDateSuccess(response: GetSearchTodoByDateResponse) {
        dismissLoadingDialog()

//      맨 처음(page=0) -> 검색결과가 하나라도 있으면
        if (page==0 && response.result.todo.isNotEmpty()){
//            Log.d("둥지", "둥지있음")

            onedaylist.addAll(response.result.todo)
            onedayadapter = TodoOneDayManagerAdapter(this, onedaylist, onedayisOwnerlist, supportFragmentManager)
            binding.todoManagerRecyclerview.adapter = onedayadapter
        }
//      page=1부터 불러오고, 둥지가 있으면 추가해줘야함 ->
        else if (page!=0 && response.result.todo.isNotEmpty()){
//            Log.d("둥지", "둥지추가")
            onedaylist.addAll(response.result.todo)
            onedayadapter.notifyItemInserted(onedaylist.size-1)
        }

//        페이지추가 끝
        else if (page!=0 && response.result.todo.isNullOrEmpty()){
//            Log.d("둥지", "둥지끝")
            istodoend = true
        }
//      없으면
        else if (page==0 && response.result.todo.isNullOrEmpty()){
            showCustomToast("검색 결과가 없습니다")
        }
    }

    override fun onGetSearchTodoByDateFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onDeleteAllOneDayTodoSuccess(response: DeleteAllOneDayTodoResponse) {
        dismissLoadingDialog()
        showCustomToast("하루만 할일이 모두 삭제되었습니다")

        page = 0
        onedaylist.clear()
        istodoend = false
        showLoadingDialog(this)
        TodoService(this).tryGetOneDayTodo(roomId, page)
    }

    override fun onDeleteAllOneDayTodoFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onDeleteAllRepeatTodoSuccess(response: DeleteAllRepeatTodoResponse) {
        dismissLoadingDialog()
        showCustomToast("반복 할일이 모두 삭제되었습니다")

        page = 0
        repeatlist.clear()
        istodoend = false
        showLoadingDialog(this)
        TodoService(this).tryGetRepeatTodo(roomId, page)
    }

    override fun onDeleteAllRepeatTodoFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }
}