package com.nestmate.nm1.src.main.home.nest.calendar

import com.nestmate.nm1.src.main.home.nest.calendar.model.*

interface CalendarActivityView {
    fun onAddCalendarSuccess(response: AddCalendarResponse)
    fun onAddCalendarFailure(message: String)

    fun onPutCalendarSuccess(response: PutCalendarResponse)
    fun onPutCalendarFailure(message: String)

    fun onDeleteCalendarSuccess(response: DeleteCalendarResponse)
    fun onDeleteCalendarFailure(message: String)

    fun onGetCalendarSuccess(response: GetCalendarResponse)
    fun onGetCalendarFailure(message: String)

    fun onGetDetailCalendarSuccess(response: GetDetailCalendarResponse)
    fun onGetDetailCalendarFailure(message: String)
}