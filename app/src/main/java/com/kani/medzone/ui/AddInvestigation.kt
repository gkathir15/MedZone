package com.kani.medzone.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kani.medzone.Constants
import com.kani.medzone.R
import com.kani.medzone.db.Reading
import com.kani.medzone.db.Report
import com.kani.medzone.ui.adapter.ReadingAdapter
import com.kani.medzone.vm.ActivityViewModel
import com.kani.medzone.vm.AddInvestigationViewModel
import kotlinx.android.synthetic.main.fragment_add_investigation.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AddInvestigation : Fragment() {
   // private val vm by viewModels<AddInvestigationViewModel>()
    private val homeViewModel by activityViewModels<ActivityViewModel>()
    private var readingAdapter: ReadingAdapter? = null
    private val list:ArrayList<Reading> = ArrayList(0)

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
        list.add(Reading("", ""))

        readingAdapter = ReadingAdapter(list)
        recycler.also {
            it.adapter = readingAdapter
            it.layoutManager = LinearLayoutManager(requireContext())
        }

        Addlabel.setOnClickListener {
            if(list.isNotEmpty()) {
                if (list.asReversed().last().name?.isNotEmpty() == true) {
                    list.add(Reading("", ""))
                    readingAdapter?.notifyDataSetChanged()
                }
            }
        }

        proceedBtn.setOnClickListener {
            GlobalScope.launch {
                homeViewModel.databaseInstance().reportDao()
                    .insert(readingAdapter?.getData()?.let { it1 ->
                        Report(
                            0, header.text.toString(), System.currentTimeMillis(),
                            it1
                        )
                    }!!)
            }
            (parentFragment as InvestigationFragment).removeAddInvestFrag(this)
        }


    }

}