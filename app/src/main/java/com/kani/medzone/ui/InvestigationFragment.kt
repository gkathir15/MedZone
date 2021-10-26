package com.kani.medzone.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import com.kani.medzone.Constants
import com.kani.medzone.R
import com.kani.medzone.db.Report
import com.kani.medzone.ui.adapter.ReportsAdapter
import com.kani.medzone.vm.ActivityViewModel
import kotlinx.android.synthetic.main.fragment_investigation.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class InvestigationFragment : Fragment() {

    private val homeViewModel by activityViewModels<ActivityViewModel>()
    private  var reportsAdapter:ReportsAdapter?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_investigation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reportsAdapter = ReportsAdapter(homeViewModel.investigationList.value?: ArrayList(0),requireContext())
        homeViewModel.investigationList.observe(viewLifecycleOwner,{
            reportsAdapter?.updateList(it)
        })
        addInvestigation.setOnClickListener {
            showInvestigationDialog()
        }
        investigationList.also {
            it.layoutManager = LinearLayoutManager(requireContext())
            it.adapter = reportsAdapter
        }

    }


    private fun showInvestigationDialog() {


        val investigationDialog = Dialog(requireContext())
        investigationDialog.setCanceledOnTouchOutside(false)
        val dialogLayout: View = layoutInflater.inflate(R.layout.add_investigation_dialog, null)


        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            R.layout.dropdown_item,
            Constants.type
        )

        val editTextFilledExposedDropdown: AutoCompleteTextView = dialogLayout.findViewById(R.id.investigationDropdown)

        editTextFilledExposedDropdown.setAdapter(adapter)
        dialogLayout.findViewById<MaterialButton>(R.id.proceedBtn).setOnClickListener {
            val selected =
               editTextFilledExposedDropdown.text.toString()
            investigationDialog.dismiss()

            val frag = AddInvestigation().also {
                it.arguments = bundleOf(Pair(Constants.NAME, selected))
            }
            childFragmentManager.beginTransaction().add(R.id.investroot, frag).commit()
            addInvestigation.visibility = View.GONE
        }
        investigationDialog.setContentView(dialogLayout)
        investigationDialog.show()


    }

    fun removeAddInvestFrag(addInvestigationFrag: AddInvestigation) {
        childFragmentManager.beginTransaction().remove(addInvestigationFrag).commit()
        investigationList.visibility= View.VISIBLE
        addInvestigation.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            homeViewModel.fetchInvestigation()
        }
    }


}