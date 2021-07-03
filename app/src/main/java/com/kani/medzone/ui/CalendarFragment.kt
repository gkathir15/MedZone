package com.kani.medzone.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kani.medzone.vm.ActivityViewModel
import com.kani.medzone.R
import com.kani.medzone.ui.adapter.StringAdapter
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import kotlinx.android.synthetic.main.fragment_calendar.*

class CalendarFragment : Fragment() {

    private val homeViewModel by activityViewModels<ActivityViewModel>()
    private var list = ArrayList<String>()
    private  var stringadapter:StringAdapter?=null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calendar.setOnDateChangedListener(object :DatePickerDialog.OnDateSetListener,
            OnDateSelectedListener {
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

            }

            override fun onDateSelected(
                widget: MaterialCalendarView,
                date: CalendarDay,
                selected: Boolean
            ) {
                list.clear()
                 homeViewModel.tabEntryList.value?.forEach {
                     it.tablet.name?.let { it1 -> list.add(it1) }
                 }

                stringadapter?.notifyDataSetChanged()

            }
        })
        stringadapter = StringAdapter(list)
        calendarDataRecycler.also {
            it.layoutManager = LinearLayoutManager(requireContext())
            it.adapter = stringadapter
        }
    }
}