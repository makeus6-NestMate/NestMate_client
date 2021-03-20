package com.example.nm1.src.main.home.nest.todo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.nm1.R
import com.example.nm1.databinding.DialogTodoAddBinding
import com.example.nm1.src.main.home.nest.todo.models.AddOneDayTodoResponse
import com.example.nm1.src.main.home.nest.todo.models.AddRepeatTodoResponse
import java.text.SimpleDateFormat
import java.util.*

class TodoAddDialogFragment : DialogFragment(), TodoFragmentView {
    var windowManager: WindowManager? = null
    var display: Display? = null
    var size: Point? = null

    private lateinit var binding: DialogTodoAddBinding
    private val isselected = Array(7){false}
    private val isrepeat = Array(2){false}

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
            if (isselected[0]) {
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
            isselected[0] = !isselected[0]
        }
        binding.todoBtnTue.setOnClickListener {
            if (isselected[1]) {
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
            isselected[1] = !isselected[1]
        }
        binding.todoBtnWed.setOnClickListener {
            if (isselected[2]) {
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
            isselected[2] = !isselected[2]
        }
        binding.todoBtnThu.setOnClickListener {
            if (isselected[3]) {
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
            isselected[3] = !isselected[3]
        }
        binding.todoBtnFri.setOnClickListener {
            if (isselected[4]) {
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
            isselected[4] = !isselected[4]
        }
        binding.todoBtnSat.setOnClickListener {
            if (isselected[5]) {
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
            isselected[5] = !isselected[5]
        }
        binding.todoBtnSun.setOnClickListener {
            if (isselected[6]) {
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
            isselected[6] = !isselected[6]
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

//       확인버튼 -> 할일 등록
//       반복/하루만 구분할 필요 O -> 날짜선택 / 요일선택이 다름
        binding.todoBtnConfirm.setOnClickListener {

        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        var params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size!!.x
        params?.width = (deviceWidth*0.75).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
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