package com.example.nm1.src.main.home.nest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nm1.CalendarPagerFragmentStateAdapter
import com.example.nm1.R
import kotlinx.android.synthetic.main.calendar_view.*

class CalendarViewPagerFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.calendar_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendarPagerAdapter = CalendarPagerFragmentStateAdapter(requireActivity())
        calvpInner.adapter=calendarPagerAdapter
        calvpInner.setCurrentItem(calendarPagerAdapter.calendarPosition, false)
    }
}
