package com.kani.medzone.ui

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.kani.medzone.vm.ActivityViewModel
import com.kani.medzone.R
import com.kani.medzone.ui.adapter.StringAdapter
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import kotlinx.android.synthetic.main.fragment_calendar.*

import com.kani.medzone.vm.EventEntry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class CalendarFragment : Fragment() {

    private val homeViewModel by activityViewModels<ActivityViewModel>()
    private var list = ArrayList<String>()
    private var eventDateList = ArrayList<CalendarDay>()
    private var tabletDateList = ArrayList<CalendarDay>()
    private  var stringadapter:StringAdapter?=null
    private  var eventList = ArrayList<EventEntry>()

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
                for(i in eventList)
                {
                    i.detail?.let { list.add(it) }
                    val date1 = Date(i.time)
                    eventDateList.add(CalendarDay.from(date1.year,date1.month+1,date1.date))
                }
                 homeViewModel.tabEntryList.value?.distinctBy { it.tablet.tabletid }?.forEach {
                     it.tablet.name?.let { it1 -> list.add(it1) }
                     val date1 = Date(it.date!!)
                     tabletDateList.add(CalendarDay.from(date1.year,date1.month+1,date1.date))
                 }

                stringadapter?.notifyDataSetChanged()

            }
        })
        stringadapter = StringAdapter(list)
        calendarDataRecycler.also {
            it.layoutManager = LinearLayoutManager(requireContext())
            it.adapter = stringadapter
        }

        addEvent.setOnClickListener {
            showAddEventDialog()
        }

             homeViewModel.eventEntryList.observe(viewLifecycleOwner,{
                 eventList = it
list.clear()
                 for(i in eventList)
                 {
                     i.detail?.let { list.add(it) }

                 }
                 homeViewModel.tabEntryList.value?.forEach {
                     it.tablet.name?.let { it1 -> list.add(it1)
                     }
                 }

                 calendar.addDecorators(
                     EventDecorator(R.color.calendar_highlighted_day_bg,eventDateList),
                     TabletDecorator(R.color.calendar_selected_day_bg,tabletDateList)
                 )


                 stringadapter?.notifyDataSetChanged()
             })






    }
    fun showAddEventDialog()
    {       val event=EventEntry(0,0,null,true)
        val builder = Dialog(requireContext())
        builder.setCanceledOnTouchOutside(true)
        builder.setCanceledOnTouchOutside(true)
        val inflater = layoutInflater
        val dialogLayout: View = inflater.inflate(R.layout.fragment_event_entry, null)
        val eventEt  = dialogLayout.findViewById<TextInputEditText>(R.id.eventEt)
        val dateTv = dialogLayout.findViewById<TextView>(R.id.date)
        val doneBtn = dialogLayout.findViewById<MaterialButton>(R.id.doneBtn)

        doneBtn.setOnClickListener {
            event.detail = eventEt.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                homeViewModel.databaseInstance().eventEntryDao().also {
                    it.insert(event)
                    it.getAll()
                }
            }
            builder.dismiss()
        }
        dateTv.setOnClickListener {

            val c = Calendar.getInstance()
            val mYear = c[Calendar.YEAR]
            val mMonth = c[Calendar.MONTH]
            val  mDay = c[Calendar.DAY_OF_MONTH]


            val datePickerDialog = DatePickerDialog(requireContext(),
                { view, year, monthOfYear, dayOfMonth ->
                    val sel = Calendar.getInstance()
                    sel.set(Calendar.YEAR,year)
                    sel.set(Calendar.MONTH,mMonth)
                    sel.set(Calendar.DATE,dayOfMonth)
                    dateTv.text = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    event.time = sel.timeInMillis
                }, mYear, mMonth, mDay
            )
            datePickerDialog.show()


        }

        builder.setContentView(dialogLayout)
        builder.show()
    }
}