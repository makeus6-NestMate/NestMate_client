package com.example.nm1.src.main.home.nest

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
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
        isCancelable=true
    }

    private lateinit var binding: DialogCalendarAddBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogCalendarAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setBackgroundColor(resources.getColor(R.color.transparent))
        val origin : String = (activity as CalendarAddActivity).calendar_category_pick_2.category_random.calendar_category_name.text.toString()
        binding.calendarAddTxt.onMyTextChanged {
            (activity as CalendarAddActivity).changeRandomCate(binding.calendarAddTxt.text.toString())
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