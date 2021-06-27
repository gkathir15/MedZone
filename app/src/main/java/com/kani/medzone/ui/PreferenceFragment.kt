package com.kani.medzone.ui

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.appcompat.app.AlertDialog
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import com.kani.medzone.Constants
import com.kani.medzone.MainActivity
import com.kani.medzone.R
import com.kani.medzone.hoursToAM_PM
import kotlinx.android.synthetic.main.fragment_preference.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class PreferenceFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_preference, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if(arguments?.getBoolean("isFirst") == true) {
            GlobalScope.launch {
                (requireActivity() as MainActivity).getDataStore().edit {
                    it[intPreferencesKey(Constants.BREAKFAST_Hour)] = 9
                    it[intPreferencesKey(Constants.BREAKFAST_min)] = 30

                    it[intPreferencesKey(Constants.LUNCH_Hour)] = 13
                    it[intPreferencesKey(Constants.LUNCH_min)] = 30

                    it[intPreferencesKey(Constants.DINNER_Hour)] = 20
                    it[intPreferencesKey(Constants.DINNER_min)] = 30

                    it[intPreferencesKey(Constants.EVENING_Hour)] = 17
                    it[intPreferencesKey(Constants.EVENING_Min)] = 30
                    GlobalScope.launch(Dispatchers.Main) {
                        breakfastTime.text = ("BreakFast ${
                            it[intPreferencesKey(Constants.BREAKFAST_Hour)] ?: 9.hoursToAM_PM(
                                it[intPreferencesKey(
                                    Constants.BREAKFAST_min
                                )] ?: 30
                            )
                        }")
                        lunchTime.text = ("Lunch ${
                            it[intPreferencesKey(Constants.LUNCH_Hour)] ?: 13.hoursToAM_PM(
                                it[intPreferencesKey(
                                    Constants.LUNCH_min
                                )] ?: 30
                            )
                        }")
                        dinerTime.text = ("Dinner ${
                            it[intPreferencesKey(Constants.DINNER_Hour)] ?: 20.hoursToAM_PM(
                                it[intPreferencesKey(
                                    Constants.DINNER_min
                                )] ?: 30
                            )
                        }")
                        eveningTime.text = ("Evening ${
                            it[intPreferencesKey(Constants.EVENING_Hour)] ?: 17.hoursToAM_PM(
                                it[intPreferencesKey(
                                    Constants.EVENING_Min
                                )] ?: 30
                            )
                        }")
                        snoozeLabel.text =
                            ("Snooze By ${it[intPreferencesKey(Constants.SNOOZETIME)] ?: 5} Minutes")
                    }
                }

                }

        }





        breakfastEdit.setOnClickListener {
            val calendar = Calendar.getInstance()
          val timePickerDialog = TimePickerDialog(requireContext(),
              { view, hourOfDay, minute ->
                  breakfastTime.text = ("BreakFast alarm time ${hourOfDay.hoursToAM_PM(minute)}")
                  GlobalScope.launch {
                      (requireActivity() as MainActivity).getDataStore().edit {
                          it[intPreferencesKey(Constants.BREAKFAST_Hour)] = hourOfDay
                          it[intPreferencesKey(Constants.BREAKFAST_min)] = minute


                      }

                  }
              },calendar.get(Calendar.HOUR),calendar.get(Calendar.MINUTE),false)

            timePickerDialog.show()
        }
        linchEdit.setOnClickListener {
            val calendar = Calendar.getInstance()
          val timePickerDialog = TimePickerDialog(requireContext(),
              { view, hourOfDay, minute ->
                  lunchTime.text = ("Lunch ${hourOfDay.hoursToAM_PM(minute)}")
                  GlobalScope.launch {

                      (requireActivity() as MainActivity).getDataStore().edit {
                          it[intPreferencesKey(Constants.LUNCH_Hour)] = hourOfDay
                          it[intPreferencesKey(Constants.LUNCH_min)] = minute

                      }

                  }
              },calendar.get(Calendar.HOUR),calendar.get(Calendar.MINUTE),false)

            timePickerDialog.show()
        }
        dinnerEdit.setOnClickListener {
            val calendar = Calendar.getInstance()
          val timePickerDialog = TimePickerDialog(requireContext(),
              { view, hourOfDay, minute ->
                  dinerTime.text = ("Dinner ${hourOfDay.hoursToAM_PM(minute)}")
                  GlobalScope.launch {
                      (requireActivity() as MainActivity).getDataStore().edit {
                          it[intPreferencesKey(Constants.DINNER_Hour)] = hourOfDay
                          it[intPreferencesKey(Constants.DINNER_min)] = minute


                      }

                  }
              },calendar.get(Calendar.HOUR),calendar.get(Calendar.MINUTE),false)

            timePickerDialog.show()
        }
        eveningEdit.setOnClickListener {
            val calendar = Calendar.getInstance()
            val timePickerDialog = TimePickerDialog(requireContext(),
                { view, hourOfDay, minute ->
                    eveningTime.text = ("Evening ${hourOfDay.hoursToAM_PM(minute)}")
                    GlobalScope.launch {
                        (requireActivity() as MainActivity).getDataStore().edit {
                            it[intPreferencesKey(Constants.EVENING_Hour)] = hourOfDay
                            it[intPreferencesKey(Constants.EVENING_Min)] = minute
                        }

                    }
                },calendar.get(Calendar.HOUR),calendar.get(Calendar.MINUTE),false)

            timePickerDialog.show()
        }
        snoozeEdit.setOnClickListener {
         showNumberPicker()
        }

//            if(care_takerNoEt.text?.isNotBlank() == true)
//            {
//
//            }

        SaveBtn.setOnClickListener {

            (requireActivity() as MainActivity).also {
                it.getPreferences()?.edit()?.putBoolean(Constants.ISLoggedIN,true)?.apply()
                it.removeFrag(this)
                it.showTabsView()
            }
        }

    }

private fun showNumberPicker()
{
    val d: AlertDialog.Builder = AlertDialog.Builder(requireContext())
    val inflater = this.layoutInflater
    val dialogView: View = inflater.inflate(R.layout.number_picker_dialog, null)
    d.setTitle("Title")
    d.setMessage("Message")
    d.setView(dialogView)
    val numberPicker = dialogView.findViewById<View>(R.id.dialog_number_picker) as NumberPicker
    numberPicker.maxValue = 20
    numberPicker.minValue = 5
    numberPicker.wrapSelectorWheel = false
    val alertDialog: AlertDialog = d.create()
    numberPicker.setOnValueChangedListener { _, i, i1 ->
        GlobalScope.launch {
            (requireActivity() as MainActivity).getDataStore().edit {
                it[intPreferencesKey(Constants.SNOOZETIME)] = i1
            }
        }
    }
    d.setPositiveButton("Done"
    ) { _, i ->
        alertDialog.dismiss()
    }
    d.setNegativeButton("Cancel"
    ) { _, i ->
        alertDialog.dismiss()
    }

    alertDialog.show()
}



}
