package com.nestmate.nm1.src.main.home.nest.calendar

import android.os.Bundle
import android.view.View
import com.nestmate.nm1.CalendarPagerFragmentStateAdapter
import com.nestmate.nm1.R
import com.nestmate.nm1.config.BaseFragment
import com.nestmate.nm1.databinding.CalendarViewBinding
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
