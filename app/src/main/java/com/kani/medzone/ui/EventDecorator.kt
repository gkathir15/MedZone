package com.kani.medzone.ui

import com.prolificinteractive.materialcalendarview.spans.DotSpan

import com.prolificinteractive.materialcalendarview.DayViewFacade

import com.prolificinteractive.materialcalendarview.CalendarDay

import com.prolificinteractive.materialcalendarview.DayViewDecorator




/**Created by Guru kathir.J on 12,October,2021 **/
class EventDecorator(color: Int, dates: Collection<CalendarDay>) :
    DayViewDecorator {
    private val color: Int
    private val dates: HashSet<CalendarDay>
    override fun shouldDecorate(day: CalendarDay): Boolean {
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade) {
        view.addSpan(DotSpan(5f, color))
    }

    init {
        this.color = color
        this.dates = HashSet(dates)
    }
}