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

class TodoAddDialog : DialogFragment(), TodoView {
    private var windowManager: WindowManager? = null
    private var display: Display? = null
    var size: Point? = null
    private lateinit var mLoadingDialog: LoadingDialog

    private lateinit var binding: DialogTodoAddBinding
    private val selecteddaylist = Array(7){false}
    private val isrepeat = Array(2){false}

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
        binding.todoBtnMon.setOnClickListener {
//            선택된 경우에 클릭했을때
            if (selecteddaylist[0]) {
                binding.todoBtnMon.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.light_gray
                    )
                )
                binding.todoBtnMon.setBackgroundResource(R.drawable.todo_btn_notclickedday)
            } else {
                binding.todoBtnMon.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.daybuttonmint
                    )
                )
                binding.todoBtnMon.setBackgroundResource(R.drawable.todo_btn_clickedday)
            }
            selecteddaylist[0] = !selecteddaylist[0]
        }
        binding.todoBtnTue.setOnClickListener {
            if (selecteddaylist[1]) {
                binding.todoBtnTue.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.light_gray
                    )
                )
                binding.todoBtnTue.setBackgroundResource(R.drawable.todo_btn_notclickedday)
            } else {
                binding.todoBtnTue.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.daybuttonmint
                    )
                )
                binding.todoBtnTue.setBackgroundResource(R.drawable.todo_btn_clickedday)
            }
            selecteddaylist[1] = !selecteddaylist[1]
        }
        binding.todoBtnWed.setOnClickListener {
            if (selecteddaylist[2]) {
                binding.todoBtnWed.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.light_gray
                    )
                )
                binding.todoBtnWed.setBackgroundResource(R.drawable.todo_btn_notclickedday)
            } else {
                binding.todoBtnWed.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.daybuttonmint
                    )
                )
                binding.todoBtnWed.setBackgroundResource(R.drawable.todo_btn_clickedday)
            }
            selecteddaylist[2] = !selecteddaylist[2]
        }
        binding.todoBtnThu.setOnClickListener {
            if (selecteddaylist[3]) {
                binding.todoBtnThu.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.light_gray
                    )
                )
                binding.todoBtnThu.setBackgroundResource(R.drawable.todo_btn_notclickedday)
            } else {
                binding.todoBtnThu.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.daybuttonmint
                    )
                )
                binding.todoBtnThu.setBackgroundResource(R.drawable.todo_btn_clickedday)
            }
            selecteddaylist[3] = !selecteddaylist[3]
        }
        binding.todoBtnFri.setOnClickListener {
            if (selecteddaylist[4]) {
                binding.todoBtnFri.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.light_gray
                    )
                )
                binding.todoBtnFri.setBackgroundResource(R.drawable.todo_btn_notclickedday)
            } else {
                binding.todoBtnFri.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.daybuttonmint
                    )
                )
                binding.todoBtnFri.setBackgroundResource(R.drawable.todo_btn_clickedday)
            }
            selecteddaylist[4] = !selecteddaylist[4]
        }
        binding.todoBtnSat.setOnClickListener {
            if (selecteddaylist[5]) {
                binding.todoBtnSat.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.light_gray
                    )
                )
                binding.todoBtnSat.setBackgroundResource(R.drawable.todo_btn_notclickedday)
            } else {
                binding.todoBtnSat.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.daybuttonmint
                    )
                )
                binding.todoBtnSat.setBackgroundResource(R.drawable.todo_btn_clickedday)
            }
            selecteddaylist[5] = !selecteddaylist[5]
        }
        binding.todoBtnSun.setOnClickListener {
            if (selecteddaylist[6]) {
                binding.todoBtnSun.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.light_gray
                    )
                )
                binding.todoBtnSun.setBackgroundResource(R.drawable.todo_btn_notclickedday)
            } else {
                binding.todoBtnSun.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.daybuttonmint
                    )
                )
                binding.todoBtnSun.setBackgroundResource(R.drawable.todo_btn_clickedday)
            }
            selecteddaylist[6] = !selecteddaylist[6]
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
            if (isrepeat[0]){
                if(binding.todoEdtTitle.text.isNotEmpty() && selecteddaylist.contains(true) && binding.todoTimepicker.text.isNotEmpty()){
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
        dismissLoadingDialog()
        binding.todoEdtTitle.text.clear() //할일 제목 비우기
        this.dismiss()
//        val bundle = bundleOf("todoadd_one_ok" to "ok")
//        // 요청키로 수신측의 리스너에 값을 전달
//        setFragmentResult("todoadd_one", bundle)
    }

    override fun onAddOneDayTodoFailure(message: String) {
        dismissLoadingDialog()
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onAddRepeatTodoSuccess(response: AddRepeatTodoResponse) {
        dismissLoadingDialog()

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