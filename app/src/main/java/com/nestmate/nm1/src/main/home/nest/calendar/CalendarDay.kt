package com.nestmate.nm1.src.main.home.nest.calendar

import com.nestmate.nm1.src.main.home.nest.calendar.model.CalendarInfo
import java.util.*

class CalendarDay (date: Date) {

    companion object {
        const val DAYS_OF_WEEK = 7
        const val LOW_OF_CALENDAR = 6
    }

    val calendar = Calendar.getInstance()

    var prevTail = 0
    var nextHead = 0
    var currentMaxDate = 0

    var dateList = arrayListOf<Int>()
    var cateList = arrayListOf<CalendarInfo?>()

    init {
        // 달력의 시간을 입력받은 date로 설정
        calendar.time = date
    }

    fun initBaseCalendar(calendarList: List<CalendarInfo>) {
        makeMonthDate(calendarList)
    }

    private fun makeMonthDate(calendarList: List<CalendarInfo>) {

        dateList.clear()
        cateList.clear()

        // 1일부터 시작
        calendar.set(Calendar.DATE, 1)

        // DAY_OF_MONTH - 달의 몇번째 일인지(12일이면 12)
        // getActualMaximum - 셋팅된 날짜의 마지막 일
        currentMaxDate = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        // 해당 주의 몇 번째 날인지(일요일이 1, 월요일이 2)
        // prev니까 1을 빼준다.
        prevTail = calendar.get(Calendar.DAY_OF_WEEK) - 1

        makePrevTail(calendar.clone() as Calendar)
        makeCurrentMonth(calendar, calendarList)

        // 전체 칸 개수는 LOW_OF_CALENDAR * DAYS_OF_WEEK이다.
        // 거기서 이전 prevTail과 현재 currentMaxDate 더한 값을 빼주면 나머지는 다음달이다.
        nextHead = LOW_OF_CALENDAR * DAYS_OF_WEEK - (prevTail + currentMaxDate)
        makeNextHead()
    }

    private fun makePrevTail(calendar: Calendar) {
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1)
        val maxDate = calendar.getActualMaximum(Calendar.DATE)
        var maxOffsetDate = maxDate - prevTail

        for (i in 1..prevTail) {
            dateList.add(++maxOffsetDate)
            cateList.add(null)
        }
    }

    private fun makeCurrentMonth(calendar: Calendar, calendarList: List<CalendarInfo>) {
        // 이전 달의 요일이 몇개 들어가있는지
        val initNum = dateList.size
        for (i in 1..calendar.getActualMaximum(Calendar.DATE)) {
            dateList.add(i)
            cateList.add(null)
        }
        for(cate in calendarList){
            // 카테고리가 있는 일에 null 대신 실 데이터 넣음
            var idx = cate.time.substring(8,10).toInt()
            // 이전 달의 요일 개수를 미리 깔아주고 그 위에 이번 달의 요일 개수를 쌓아야 한다.
            // index는 0부터 시작이니까 -1을 빼줘야 한다.
            cateList[initNum+idx-1]=cate
        }
    }

    private fun makeNextHead() {
        var date = 1

        for (i in 1..nextHead) dateList.add(date++)
        for (i in 1..nextHead) cateList.add(null)
    }

}
