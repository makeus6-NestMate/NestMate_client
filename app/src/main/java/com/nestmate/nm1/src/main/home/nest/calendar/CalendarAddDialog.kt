package com.nestmate.nm1.src.main.home.nest.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.nestmate.nm1.R
import com.nestmate.nm1.databinding.DialogCalendarAddBinding
import com.nestmate.nm1.util.onMyTextChanged
import kotlinx.android.synthetic.main.activity_calendar_add.*
import kotlinx.android.synthetic.main.activity_calendar_add.view.*
import kotlinx.android.synthetic.main.calendar_category.view.*
import kotlinx.android.synthetic.main.dialog_calendar_add.*

class CalendarAddDialog : DialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable=false
    }

    private lateinit var binding: DialogCalendarAddBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogCalendarAddBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.rec_design)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setBackgroundColor(resources.getColor(R.color.transparent))
        val origin = (activity as CalendarAddActivity).calendar_category_pick_2.category_random.calendar_category_name.text.toString()
        binding.calendarAddTxt.onMyTextChanged {
            if(binding.calendarAddTxt.text.length>7){
                binding.calendarAddTxt.text.delete(binding.calendarAddTxt.selectionEnd -1, binding.calendarAddTxt.selectionStart)
                val imm = activity?.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.calendarAddTxt.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS)
            }
            else (activity as CalendarAddActivity).changeRandomCate(binding.calendarAddTxt.text.toString())
            if(binding.calendarAddTxt.text.toString().trim()!=""){
                binding.calendarAddYesBtn.isEnabled = true
                binding.calendarAddYesBtn.setBackgroundResource(R.drawable.roundrec_design_active_bg)
            }
            else{
                binding.calendarAddYesBtn.isEnabled = false
                binding.calendarAddYesBtn.setBackgroundResource(R.drawable.roundrec_design_inactive_bg)
            }
        }
        binding.calendarAddNoBtn.setOnClickListener {
            (activity as CalendarAddActivity).changeRandomCate(origin)
            dismiss()
        }
        binding.calendarAddYesBtn.setOnClickListener {
            dismiss()
        }
    }
}