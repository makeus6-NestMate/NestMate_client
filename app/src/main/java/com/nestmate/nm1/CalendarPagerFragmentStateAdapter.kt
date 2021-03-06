package com.nestmate.nm1

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nestmate.nm1.src.main.home.nest.calendar.CalendarFragment
import kotlin.collections.ArrayList

class CalendarPagerFragmentStateAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    var calFragments : ArrayList<CalendarFragment> = ArrayList()
    val calendarPosition = Int.MAX_VALUE/2

    override fun getItemCount(): Int = Int.MAX_VALUE

    override fun createFragment(position: Int): Fragment {
        val calendarFragment= CalendarFragment()
        calendarFragment.pageIndex=position
        return calendarFragment
    }

}