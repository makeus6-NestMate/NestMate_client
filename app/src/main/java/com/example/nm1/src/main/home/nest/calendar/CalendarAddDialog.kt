package com.example.nm1.src.main.home.nest.calendar

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.nm1.R
import com.example.nm1.databinding.DialogCalendarAddBinding
import com.example.nm1.util.onMyTextChanged
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
        val origin : String = (activity as CalendarAddActivity).calendar_category_pick_2.category_random.calendar_category_name.text.toString()
        binding.calendarAddTxt.onMyTextChanged {
            (activity as CalendarAddActivity).changeRandomCate(binding.calendarAddTxt.text.toString())
            if(binding.calendarAddTxt.text.toString()!="" || binding.calendarAddTxt.text[0]!=' '){
                binding.calendarAddYesBtn.setBackgroundResource(R.drawable.roundrec_design_active_bg)
            }
            else{
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