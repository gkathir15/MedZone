package com.kani.medzone.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.button.MaterialButton
import com.kani.medzone.Constants
import com.kani.medzone.R
import com.kani.medzone.vm.ActivityViewModel
import kotlinx.android.synthetic.main.fragment_investigation.*


class InvestigationFragment : Fragment() {

    private val homeViewModel by activityViewModels<ActivityViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_investigation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addInvestigation.setOnClickListener {
            showInvestigationDialog()
        }
    }


    private fun showInvestigationDialog() {


        val builder = Dialog(requireContext())
        builder.setCanceledOnTouchOutside(false)
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
            builder.dismiss()

            val frag = AddInvestigation().also {
                it.arguments = bundleOf(Pair(Constants.NAME, selected))
            }
            childFragmentManager.beginTransaction().add(R.id.investroot, frag).commit()
            addInvestigation.visibility = View.GONE
        }
        builder.setContentView(dialogLayout)
        builder.show()


    }

    fun removeAddInvestFrag(addInvestigationFrag: AddInvestigation) {
        childFragmentManager.beginTransaction().remove(addInvestigationFrag).commit()
        addInvestigation.visibility = View.VISIBLE
    }


}