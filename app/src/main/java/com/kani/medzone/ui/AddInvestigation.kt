package com.kani.medzone.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kani.medzone.Constants
import com.kani.medzone.R
import com.kani.medzone.db.Reading
import com.kani.medzone.db.Report
import com.kani.medzone.ui.adapter.ReadingAdapter
import com.kani.medzone.vm.ActivityViewModel
import kotlinx.android.synthetic.main.fragment_add_investigation.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class AddInvestigation : Fragment() {
   // private val vm by viewModels<AddInvestigationViewModel>()
    private val homeViewModel by activityViewModels<ActivityViewModel>()
    private var readingAdapter: ReadingAdapter? = null
    private val list:ArrayList<Reading> = ArrayList(0)
    private var date:Long?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_investigation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        header.text = arguments?.getString(Constants.NAME)
        list.add(Reading("", "",""))

        readingAdapter = ReadingAdapter(list,requireContext(),arguments?.getString(Constants.NAME))
        recycler.also {
            it.adapter = readingAdapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }

        Addlabel.setOnClickListener {
            if(list.isNotEmpty()) {
                if (list.asReversed().last().name?.isNotEmpty() == true) {
                    list.add(Reading("", "",""))
                    readingAdapter?.notifyDataSetChanged()
                }
            }
        }

        proceedBtn.setOnClickListener {
            if(date==null)
            {val preset = Calendar.getInstance()
                val dialog = DatePickerDialog(requireContext(),
                    { view, year, month, dayOfMonth ->
                        val setDate = Calendar.getInstance().also {
                            it.set(Calendar.YEAR,year)
                            it.set(Calendar.MONTH,month)
                            it.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                        }
                       date= setDate.timeInMillis
                        GlobalScope.launch {
                            homeViewModel.databaseInstance().reportDao()
                                .insert(readingAdapter?.getData()?.let { it1 ->
                                    Report(
                                        0, header.text.toString(), date!!,
                                        it1
                                    )
                                }!!)
                        }
                        (parentFragment as InvestigationFragment).removeAddInvestFrag(this)
                    }, preset.get(Calendar.YEAR), preset.get(Calendar.MONTH), preset.get(Calendar.DAY_OF_MONTH))
                dialog.show()
            }
        }


    }

}