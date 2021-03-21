package com.example.nm1.src.main.home.nest.todo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.nm1.R
import com.example.nm1.config.ApplicationClass
import com.example.nm1.databinding.DialogTodoAddBinding
import com.example.nm1.src.main.home.nest.todo.model.*
import com.example.nm1.util.LoadingDialog
import com.example.nm1.util.onMyTextChanged
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

class TodoEditDialog : DialogFragment(), TodoView {
    private var windowManager: WindowManager? = null
    private var display: Display? = null
    var size: Point? = null
    private lateinit var mLoadingDialog: LoadingDialog

    private lateinit var binding: DialogTodoAddBinding
    private val isdayselectedlist = Array(7){false}

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

        arguments?.getString("onedayorrepeat")

        windowManager = activity?.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
        display = windowManager!!.defaultDisplay
        size = Point()
        display!!.getSize(size)

//       반복
        if (arguments?.getString("onedayorrepeat")=="repeat"){
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
            binding.todoBtnOne.isEnabled = false //하루만 버튼 비활성화
            //  요일이 나오게
            binding.todoLayoutDate.visibility = View.INVISIBLE
            binding.todoLayoutDay.visibility = View.VISIBLE
            binding.todoTvDateday.text = "요일 선택"
        }
//       하루만
        else{
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
            binding.todoBtnRepeat.isEnabled = false //반복 버튼 비활성화

//           날짜가 나오게
            binding.todoLayoutDate.visibility = View.VISIBLE
            binding.todoLayoutDay.visibility = View.INVISIBLE
            binding.todoTvDateday.text = "날짜 선택"
        }
//       할일
//       글자수 실시간으로 보이게
        binding.todoEdtTitle.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val userinput = binding.todoEdtTitle.text.toString()
                binding.todoTvTitlelength.text = userinput.length.toString()
            }

            override fun afterTextChanged(s: Editable?) {
                val userinput = binding.todoEdtTitle.text.toString()
                binding.todoTvTitlelength.text = userinput.length.toString()
            }

        })

//       요일 선택
//        binding.todoBtnMon.setOnClickListener {
////            선택된 경우에 클릭했을때
//            if (isdayselectedlist[0]==1) {
//                binding.todoBtnMon.setTextColor(
//                    ContextCompat.getColor(
//                        requireContext(),
//                        R.color.light_gray
//                    )
//                )
//                binding.todoBtnMon.setBackgroundResource(R.drawable.todo_btn_notclickedday)
//                isdayselectedlist[0]==0
//            } else {
//                binding.todoBtnMon.setTextColor(
//                    ContextCompat.getColor(
//                        requireContext(),
//                        R.color.daybuttonmint
//                    )
//                )
//                binding.todoBtnMon.setBackgroundResource(R.drawable.todo_btn_clickedday)
//                isdayselectedlist[0]==1
//            }
//            isdayselectedlist[0] = !isdayselectedlist[0]
//        }
//        binding.todoBtnTue.setOnClickListener {
//            if (isdayselectedlist[1]==1) {
//                binding.todoBtnTue.setTextColor(
//                    ContextCompat.getColor(
//                        requireContext(),
//                        R.color.light_gray
//                    )
//                )
//                binding.todoBtnTue.setBackgroundResource(R.drawable.todo_btn_notclickedday)
//                isdayselectedlist[1]==0
//            } else {
//                binding.todoBtnTue.setTextColor(
//                    ContextCompat.getColor(
//                        requireContext(),
//                        R.color.daybuttonmint
//                    )
//                )
//                binding.todoBtnTue.setBackgroundResource(R.drawable.todo_btn_clickedday)
//                isdayselectedlist[1]==1
//            }
//        }
//        binding.todoBtnWed.setOnClickListener {
//            if (isdayselectedlist[2]) {
//                binding.todoBtnWed.setTextColor(
//                    ContextCompat.getColor(
//                        requireContext(),
//                        R.color.light_gray
//                    )
//                )
//                binding.todoBtnWed.setBackgroundResource(R.drawable.todo_btn_notclickedday)
//            } else {
//                binding.todoBtnWed.setTextColor(
//                    ContextCompat.getColor(
//                        requireContext(),
//                        R.color.daybuttonmint
//                    )
//                )
//                binding.todoBtnWed.setBackgroundResource(R.drawable.todo_btn_clickedday)
//            }
//            isdayselectedlist[2] = !isdayselectedlist[2]
//        }
//        binding.todoBtnThu.setOnClickListener {
//            if (isdayselectedlist[3]) {
//                binding.todoBtnThu.setTextColor(
//                    ContextCompat.getColor(
//                        requireContext(),
//                        R.color.light_gray
//                    )
//                )
//                binding.todoBtnThu.setBackgroundResource(R.drawable.todo_btn_notclickedday)
//            } else {
//                binding.todoBtnThu.setTextColor(
//                    ContextCompat.getColor(
//                        requireContext(),
//                        R.color.daybuttonmint
//                    )
//                )
//                binding.todoBtnThu.setBackgroundResource(R.drawable.todo_btn_clickedday)
//            }
//            isdayselectedlist[3] = !isdayselectedlist[3]
//        }
//        binding.todoBtnFri.setOnClickListener {
//            if (isdayselectedlist[4]) {
//                binding.todoBtnFri.setTextColor(
//                    ContextCompat.getColor(
//                        requireContext(),
//                        R.color.light_gray
//                    )
//                )
//                binding.todoBtnFri.setBackgroundResource(R.drawable.todo_btn_notclickedday)
//            } else {
//                binding.todoBtnFri.setTextColor(
//                    ContextCompat.getColor(
//                        requireContext(),
//                        R.color.daybuttonmint
//                    )
//                )
//                binding.todoBtnFri.setBackgroundResource(R.drawable.todo_btn_clickedday)
//            }
//            isdayselectedlist[4] = !isdayselectedlist[4]
//        }
//        binding.todoBtnSat.setOnClickListener {
//            if (isdayselectedlist[5]) {
//                binding.todoBtnSat.setTextColor(
//                    ContextCompat.getColor(
//                        requireContext(),
//                        R.color.light_gray
//                    )
//                )
//                binding.todoBtnSat.setBackgroundResource(R.drawable.todo_btn_notclickedday)
//            } else {
//                binding.todoBtnSat.setTextColor(
//                    ContextCompat.getColor(
//                        requireContext(),
//                        R.color.daybuttonmint
//                    )
//                )
//                binding.todoBtnSat.setBackgroundResource(R.drawable.todo_btn_clickedday)
//            }
//            isdayselectedlist[5] = !isdayselectedlist[5]
//        }
//        binding.todoBtnSun.setOnClickListener {
//            if (isdayselectedlist[6]) {
//                binding.todoBtnSun.setTextColor(
//                    ContextCompat.getColor(
//                        requireContext(),
//                        R.color.light_gray
//                    )
//                )
//                binding.todoBtnSun.setBackgroundResource(R.drawable.todo_btn_notclickedday)
//            } else {
//                binding.todoBtnSun.setTextColor(
//                    ContextCompat.getColor(
//                        requireContext(),
//                        R.color.daybuttonmint
//                    )
//                )
//                binding.todoBtnSun.setBackgroundResource(R.drawable.todo_btn_clickedday)
//            }
//            isdayselectedlist[6] = !isdayselectedlist[6]
//        }

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
        }

//       다 입력시 확인버튼 활성화
        binding.todoEdtTitle.onMyTextChanged {
//           반복
            if (arguments?.getString("onedayorrepeat")=="repeat"){
                if(binding.todoEdtTitle.text.isNotEmpty() && isdayselectedlist.contains(true) && binding.todoTimepicker.text.isNotEmpty()){
                    binding.todoBtnConfirm.isEnabled = true //버튼 활성화
                    binding.todoBtnConfirm.setBackgroundResource(R.drawable.memo_dialog_btn_orange_bg)
                }else{
                    binding.todoBtnConfirm.isEnabled = false
                    binding.todoBtnConfirm.setBackgroundResource(R.drawable.memo_dialog_btn_grey_bg)
                }
            }
//            하루만
            else {
                if(binding.todoEdtTitle.text.isNotEmpty() && binding.todoDatepicker.text.isNotEmpty() && binding.todoTimepicker.text.isNotEmpty()){
                    binding.todoBtnConfirm.isEnabled = true //버튼 활성화
                    binding.todoBtnConfirm.setBackgroundResource(R.drawable.memo_dialog_btn_orange_bg)
                }else{
                    binding.todoBtnConfirm.isEnabled = false
                    binding.todoBtnConfirm.setBackgroundResource(R.drawable.memo_dialog_btn_grey_bg)
                }
            }

        }

//       확인버튼 -> 할일 등록
//       반복/하루만 구분할 필요 O -> 날짜선택 / 요일선택이 다름
        binding.todoBtnConfirm.setOnClickListener {
//          하루만
            if (arguments?.getString("onedayorrepeat")=="oneday"){
                val selectedTimeString = "$selectedyear/$selectedmonth/$selectedday/$selectedhour/$selectedminute"
                val putOneDayTodo = PutOneDayTodo(arguments?.getInt("todoId")!!, binding.todoEdtTitle.text.toString(), selectedTimeString)

                showLoadingDialog(requireContext())
                TodoService(this).tryPutOneDayTodo(roomId, putOneDayTodo)
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size!!.x
        params?.width = (deviceWidth*0.76).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
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

    override fun onPutOneDayTodoSuccess(response: PutOneDayTodoResponse) {
        dismissLoadingDialog()
        if (!response.isSuccess){
            Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
        }
        else {
            dismissLoadingDialog()
            Toast.makeText(requireContext(), "수정이 완료되었습니다", Toast.LENGTH_SHORT).show()
            this.dismiss()
        }
    }

    override fun onPutOneDayTodoFailure(message: String) {
        dismissLoadingDialog()
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
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