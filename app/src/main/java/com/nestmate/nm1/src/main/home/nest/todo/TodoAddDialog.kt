package com.nestmate.nm1.src.main.home.nest.todo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.nestmate.nm1.R
import com.nestmate.nm1.config.ApplicationClass
import com.nestmate.nm1.databinding.DialogTodoAddBinding
import com.nestmate.nm1.src.main.home.nest.todo.model.*
import com.nestmate.nm1.util.LoadingDialog
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

class TodoAddDialog : DialogFragment(), TodoView {
    private var windowManager: WindowManager? = null
    private var display: Display? = null
    var size: Point? = null
    private lateinit var mLoadingDialog: LoadingDialog

    private lateinit var binding: DialogTodoAddBinding
    private var selecteddaylist = Array(7){0}
    private val isrepeat = Array(2){false}
    private val confirmcheck = Array(4){false}

//   선택한 날짜, 시간 저장
    private var selectedyear by Delegates.notNull<Int>()
    private var selectedmonth by Delegates.notNull<Int>()
    private var selectedday by Delegates.notNull<Int>()
    private var selectedhour by Delegates.notNull<Int>()
    private var selectedminute by Delegates.notNull<Int>()
    private val roomId = ApplicationClass.sSharedPreferences.getInt("roomId", 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //false로 설정해 주면 화면밖 혹은 뒤로가기 버튼시 다이얼로그라 dismiss 되지 않는다.
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogTodoAddBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        isrepeat[0] = true
        isrepeat[1] = false

        Log.d("hello", isrepeat[0].toString()+"/"+isrepeat[1].toString())

        windowManager = activity?.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
        display = windowManager!!.defaultDisplay
        size = Point()
        display!!.getSize(size)

//       반복
        binding.todoBtnRepeat.setOnClickListener {
            binding.todoBtnRepeat.setBackgroundResource(R.drawable.orange_button)
            binding.todoBtnRepeat.setTextColor(
                ContextCompat.getColor(
                    requireContext(), R.color.white
                )
            )
            binding.todoBtnOne.setBackgroundResource(R.drawable.white_button)
            binding.todoBtnOne.setTextColor(
                ContextCompat.getColor(
                    requireContext(), R.color.gray2
                )
            )
//           요일이 나오게
            binding.todoLayoutDate.visibility = View.INVISIBLE
            binding.todoLayoutDay.visibility = View.VISIBLE
            binding.todoTvDateday.text = "요일 선택"

            isrepeat[0] = true
            isrepeat[1] = false

            Log.d("hello", isrepeat[0].toString()+"/"+isrepeat[1].toString())
        }

//        하루만 버튼색 변경
        binding.todoBtnOne.setOnClickListener {
            binding.todoBtnOne.setBackgroundResource(R.drawable.orange_button)
            binding.todoBtnOne.setTextColor(
                ContextCompat.getColor(
                    requireContext(), R.color.white
                )
            )
            binding.todoBtnRepeat.setBackgroundResource(R.drawable.white_button)
            binding.todoBtnRepeat.setTextColor(
                ContextCompat.getColor(
                    requireContext(), R.color.gray2
                )
            )
//           날짜가 나오게
            binding.todoLayoutDate.visibility = View.VISIBLE
            binding.todoLayoutDay.visibility = View.INVISIBLE
            binding.todoTvDateday.text = "날짜 선택"

            isrepeat[0] = false
            isrepeat[1] = true

            Log.d("hello", isrepeat[0].toString()+"/"+isrepeat[1].toString())
        }
//       할일
//       글자수 실시간으로 보이게 & 버튼 활성화
        binding.todoEdtTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val userinput = binding.todoEdtTitle.text.toString()
                binding.todoTvTitlelength.text = userinput.length.toString()

//           반복
                if (isrepeat[0]){
                    if(binding.todoEdtTitle.text.isNotEmpty() && selecteddaylist.joinToString("")!="0000000" && binding.todoTimepicker.text.isNotEmpty()){
                        binding.todoBtnConfirm.isEnabled = true //버튼 활성화
                        binding.todoBtnConfirm.setBackgroundResource(R.drawable.memo_dialog_btn_orange_bg)
                    }else{
                        binding.todoBtnConfirm.isEnabled = false
                        binding.todoBtnConfirm.setBackgroundResource(R.drawable.memo_dialog_btn_grey_bg)
                    }
                }
//            하루만
                else if (isrepeat[1]){
                    if(binding.todoEdtTitle.text.isNotEmpty() && binding.todoDatepicker.text.isNotEmpty() && binding.todoTimepicker.text.isNotEmpty()){
                        binding.todoBtnConfirm.isEnabled = true //버튼 활성화
                        binding.todoBtnConfirm.setBackgroundResource(R.drawable.memo_dialog_btn_orange_bg)
                    }else{
                        binding.todoBtnConfirm.isEnabled = false
                        binding.todoBtnConfirm.setBackgroundResource(R.drawable.memo_dialog_btn_grey_bg)
                    }
                }
                Add_CheckActive()
            }

            override fun afterTextChanged(s: Editable?) {
                val userinput = binding.todoEdtTitle.text.toString()
                binding.todoTvTitlelength.text = userinput.length.toString()

                Add_CheckActive()
            }

        })

//       요일 선택
        binding.todoBtnMon.setOnClickListener {

//            선택된 경우에 클릭했을때
            if (selecteddaylist[0]==1) {
                binding.todoBtnMon.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.light_gray
                    )
                )
                binding.todoBtnMon.setBackgroundResource(R.drawable.todo_btn_notclickedday)
                selecteddaylist[0] = 0
            } else {
                binding.todoBtnMon.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.daybuttonmint
                    )
                )
                binding.todoBtnMon.setBackgroundResource(R.drawable.todo_btn_clickedday)
                selecteddaylist[0] = 1
            }
            Add_CheckActive()
        }
        binding.todoBtnTue.setOnClickListener {
            if (selecteddaylist[1]==1) {
                binding.todoBtnTue.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.light_gray
                    )
                )
                binding.todoBtnTue.setBackgroundResource(R.drawable.todo_btn_notclickedday)
                selecteddaylist[1] = 0
            } else {
                binding.todoBtnTue.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.daybuttonmint
                    )
                )
                binding.todoBtnTue.setBackgroundResource(R.drawable.todo_btn_clickedday)
                selecteddaylist[1] = 1
            }
            Add_CheckActive()
        }
        binding.todoBtnWed.setOnClickListener {
            if (selecteddaylist[2]==1) {
                binding.todoBtnWed.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.light_gray
                    )
                )
                binding.todoBtnWed.setBackgroundResource(R.drawable.todo_btn_notclickedday)
                selecteddaylist[2]=0
            } else {
                binding.todoBtnWed.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.daybuttonmint
                    )
                )
                binding.todoBtnWed.setBackgroundResource(R.drawable.todo_btn_clickedday)
                selecteddaylist[2]=1
            }
            Add_CheckActive()
        }
        binding.todoBtnThu.setOnClickListener {
            if (selecteddaylist[3]==1) {
                binding.todoBtnThu.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.light_gray
                    )
                )
                binding.todoBtnThu.setBackgroundResource(R.drawable.todo_btn_notclickedday)
                selecteddaylist[3] = 0
            } else {
                binding.todoBtnThu.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.daybuttonmint
                    )
                )
                binding.todoBtnThu.setBackgroundResource(R.drawable.todo_btn_clickedday)
                selecteddaylist[3] = 1
            }
            Add_CheckActive()
        }
        binding.todoBtnFri.setOnClickListener {
            if (selecteddaylist[4]==1) {
                binding.todoBtnFri.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.light_gray
                    )
                )
                binding.todoBtnFri.setBackgroundResource(R.drawable.todo_btn_notclickedday)
                selecteddaylist[4] = 0
            } else {
                binding.todoBtnFri.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.daybuttonmint
                    )
                )
                binding.todoBtnFri.setBackgroundResource(R.drawable.todo_btn_clickedday)
                selecteddaylist[4] = 1
            }
            Add_CheckActive()
        }
        binding.todoBtnSat.setOnClickListener {
            if (selecteddaylist[5]==1) {
                binding.todoBtnSat.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.light_gray
                    )
                )
                binding.todoBtnSat.setBackgroundResource(R.drawable.todo_btn_notclickedday)
                selecteddaylist[5] = 0
            } else {
                binding.todoBtnSat.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.daybuttonmint
                    )
                )
                binding.todoBtnSat.setBackgroundResource(R.drawable.todo_btn_clickedday)
                selecteddaylist[5] = 1
            }
            Add_CheckActive()
        }
        binding.todoBtnSun.setOnClickListener {
            if (selecteddaylist[6]==1) {
                binding.todoBtnSun.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.light_gray
                    )
                )
                binding.todoBtnSun.setBackgroundResource(R.drawable.todo_btn_notclickedday)
                selecteddaylist[6] = 0
            } else {
                binding.todoBtnSun.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.daybuttonmint
                    )
                )
                binding.todoBtnSun.setBackgroundResource(R.drawable.todo_btn_clickedday)
                selecteddaylist[6] = 1
            }
            Add_CheckActive()
        }

//       날짜 선택
        lateinit var selecteddate: Date
        binding.todoDatepicker.setOnClickListener {
//            datepickerdialog에 표시할 달력
            val datepickercalendar = Calendar.getInstance()
            val year = datepickercalendar.get(Calendar.YEAR)
            val month = datepickercalendar.get(Calendar.MONTH)
            val day = datepickercalendar.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(
                requireContext(),
                R.style.MyDatePickerStyle,
                { _, year, monthOfYear, dayOfMonth ->
//                  월이 0부터 시작하여 1을 더해주어야함
                    val month = monthOfYear + 1
//                   선택한 날짜의 요일을 구하기 위한 calendar
                    val calendar = Calendar.getInstance()
//                    선택한 날짜 세팅
                    calendar.set(year, monthOfYear, dayOfMonth)
                    selecteddate = calendar.time

                    val simpledateformat = SimpleDateFormat("EEEE", Locale.getDefault())
                    val dayName: String = simpledateformat.format(selecteddate)

                    binding.todoDatepicker.text = "$year.$month.$dayOfMonth ($dayName)"
                    selectedyear = year
                    selectedmonth = month
                    selectedday = dayOfMonth

                    Add_CheckActive()
                },
                year,
                month,
                day
            )
//           최소 날짜를 현재 시각 이후로
            dpd.datePicker.minDate = System.currentTimeMillis() - 1000
            dpd.show()
        }

//        시간 선택
        lateinit var selectedTime: Date
        binding.todoTimepicker.setOnClickListener {
            //timepickerdialog에 표시할 달력
            val timepickercalendar = Calendar.getInstance()
            val hour = timepickercalendar.get(Calendar.HOUR_OF_DAY)
            val minute = timepickercalendar.get(Calendar.MINUTE)
            val tpd = TimePickerDialog(
                requireContext(),
                R.style.MyTimePickerStyle,
                { _, hourOfDay, minute ->
                    val calendar = Calendar.getInstance()
//                    선택한 날짜 세팅
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minute)
                    selectedTime = calendar.time

                    var timeString: String = if (hourOfDay < 12)
                        "오전 " + hourOfDay.toString() + "시 " + minute.toString() + "분"
                    else {
                        "오후 " + (hourOfDay - 12).toString() + "시 " + minute.toString() + "분"
                    }
                    selectedhour = hourOfDay
                    selectedminute = minute
                    binding.todoTimepicker.text = timeString

                    Add_CheckActive()
                },
                hour,
                minute,
                false
            )
            tpd.show()
        }

//       취소버튼
        binding.todoBtnCancel.setOnClickListener {
            dismiss()
            binding.todoDatepicker.text = ""
            binding.todoEdtTitle.text.clear()
            binding.todoTimepicker.text= ""

            Log.d("hello", isrepeat[0].toString()+"/"+isrepeat[1].toString())
        }

//       확인버튼 -> 할일 등록
//       반복/하루만 구분할 필요 O -> 날짜선택 / 요일선택이 다름
        binding.todoBtnConfirm.setOnClickListener {
//          하루만
            if (isrepeat[1]){
                val selectedTimeString = "$selectedyear/$selectedmonth/$selectedday/$selectedhour/$selectedminute"
                val postAddOneDayTodo = PostAddOneDayTodo(selectedTimeString, binding.todoEdtTitle.text.toString(), ApplicationClass.sSharedPreferences.getInt("roomId", 0))

                showLoadingDialog(requireContext())
                TodoService(this).tryAddOneDayTodo(roomId, postAddOneDayTodo)
            }
//            반복
            else if (isrepeat[0]){
                val postAddRepeatTodo = PostAddRepeatTodo("$selectedhour/$selectedminute", selecteddaylist.joinToString(""), binding.todoEdtTitle.text.toString(), ApplicationClass.sSharedPreferences.getInt("roomId", 0))
                showLoadingDialog(requireContext())
                TodoService(this).tryAddRepeatTodo(roomId, postAddRepeatTodo)
            }

            binding.todoDatepicker.text = ""
            binding.todoEdtTitle.text.clear()

            binding.todoTimepicker.text= ""
            selecteddaylist = Array(7){0}
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size!!.x
        params?.width = (deviceWidth*0.75).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    fun Add_CheckActive(){
        var flag = -1
        confirmcheck[0]=(binding.todoEdtTitle.text).isNotEmpty()
        confirmcheck[2]=(binding.todoTimepicker.text).isNotEmpty()
        if (isrepeat[0]) { //반복
            confirmcheck[3] = (selecteddaylist.joinToString("") != "0000000")
            confirmcheck[1] = true
        }
        else if (isrepeat[1]){
            confirmcheck[3] = true
            confirmcheck[1]= (binding.todoDatepicker.text).isNotEmpty()
        }
        for(idx in confirmcheck.indices){
            if(!confirmcheck[idx]) flag = idx
        }
        if(flag==-1){
            binding.todoBtnConfirm.setBackgroundResource(R.drawable.roundrec_design_active_bg)
            binding.todoBtnConfirm.isEnabled = true
        }else{
            binding.todoBtnConfirm.setBackgroundResource(R.drawable.roundrec_design_inactive_bg)
            binding.todoBtnConfirm.isEnabled = false
        }
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
        dismissLoadingDialog()
        binding.todoEdtTitle.text.clear() //할일 제목 비우기
        this.dismiss()
        val bundle = bundleOf("todoadd_ok" to "ok")
//        // 요청키로 수신측의 리스너에 값을 전달
        setFragmentResult("todoadd", bundle)
    }

    override fun onAddOneDayTodoFailure(message: String) {
        dismissLoadingDialog()
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onAddRepeatTodoSuccess(response: AddRepeatTodoResponse) {
        dismissLoadingDialog()
        binding.todoEdtTitle.text.clear() //할일 제목 비우기
        this.dismiss()
        val bundle = bundleOf("todoadd_ok" to "ok")
//        // 요청키로 수신측의 리스너에 값을 전달
        setFragmentResult("todoadd", bundle)
    }

    override fun onAddRepeatTodoFailure(message: String) {
        dismissLoadingDialog()
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
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