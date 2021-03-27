package com.example.nm1.src.main.home.nest.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nm1.CalendarPagerFragmentStateAdapter
import com.example.nm1.R
import com.example.nm1.config.BaseFragment
import com.example.nm1.databinding.CalendarViewBinding
import com.example.nm1.databinding.ViewpagerNestTabBinding
import kotlinx.android.synthetic.main.calendar_view.*

class CalendarViewPagerFragment : BaseFragment<CalendarViewBinding>(
    CalendarViewBinding::bind,
    R.layout.calendar_view) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendarPagerAdapter = CalendarPagerFragmentStateAdapter(requireActivity())
        binding.calvpInner.adapter=calendarPagerAdapter
        binding.calvpInner.setCurrentItem(calendarPagerAdapter.calendarPosition, false)
    }
}
