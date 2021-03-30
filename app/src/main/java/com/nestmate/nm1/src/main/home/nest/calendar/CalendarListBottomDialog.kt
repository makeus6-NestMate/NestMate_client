package com.nestmate.nm1.src.main.home.nest.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nestmate.nm1.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_calendar_list_bottom.view.*

class CalendarListBottomDialog (val itemClick: (Int) -> Unit) :
    BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(R.layout.dialog_calendar_list_bottom, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.calendar_list_modify.setOnClickListener {
            itemClick(0)
            dialog?.dismiss()
        }
        view.calendar_list_delete.setOnClickListener {
            itemClick(1)
            dialog?.dismiss()
        }
    }
}