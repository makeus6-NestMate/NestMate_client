package com.example.nm1.src.main.home.nest

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nm1.R
import kotlinx.android.synthetic.main.calendar_fragment.*
import kotlinx.android.synthetic.main.calendar_fragment.view.*
import kotlinx.android.synthetic.main.calendar_item.*
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment : Fragment() {
    lateinit var mContext: Context

    var pageIndex = 0
    lateinit var currentDate: Date

    lateinit var calendar_year_month_text: TextView
    lateinit var calendar_layout: LinearLayout
    lateinit var calendar_view: RecyclerView
    lateinit var calendarAdapter: CalendarAdapter

    companion object {
        var instance: CalendarFragment? = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CalendarActivity) {
            mContext = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.calendar_fragment, container, false)
        initView(view)
        return view
    }

    fun initView(view: View) {
        pageIndex -= (Int.MAX_VALUE / 2)
        calendar_year_month_text = view.calendar_year_month_text
        calendar_layout = view.calendar_layout
        calendar_view = view.calendar_view
        setView(view)
    }

    fun setView(view: View){
        // 날짜 적용
        val date = Calendar.getInstance().run {
            add(Calendar.MONTH, pageIndex)
            time
        }
        currentDate = date
        // 포맷 적용
        var datetime: String = SimpleDateFormat(
            mContext.getString(R.string.calendar_format),
            Locale.KOREA
        ).format(date.time)
        calendar_year_month_text.setText(datetime)

        calendarAdapter=CalendarAdapter(mContext, calendar_layout, currentDate)
        calendar_view.layoutManager=GridLayoutManager(mContext, CalendarDay.DAYS_OF_WEEK)
        calendar_view.adapter=calendarAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendar_back_btn.setOnClickListener {
            pageIndex--;
            setView(view)
        }
        calendar_front_btn.setOnClickListener {
            pageIndex++;
            setView(view)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }
}