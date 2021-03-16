package com.example.nm1.src.main.home.nest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.nm1.R
import com.example.nm1.databinding.CalendarAddDialogBinding
import com.example.nm1.databinding.ChartDialogBinding

class CalendarAddDialog : DialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable=true
    }

    private lateinit var binding: CalendarAddDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CalendarAddDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setBackgroundColor(resources.getColor(R.color.transparent))
        binding.calendarAddNoBtn.setOnClickListener {
            dismiss()
        }
        binding.calendarAddYesBtn.setOnClickListener {
            dismiss()
        }
    }
}