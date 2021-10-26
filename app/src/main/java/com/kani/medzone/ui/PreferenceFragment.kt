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
import com.kani.medzone.Constants
import com.kani.medzone.MainActivity
import com.kani.medzone.R
import com.kani.medzone.hoursToAM_PM
import kotlinx.android.synthetic.main.fragment_preference.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.*


class PreferenceFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_preference, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (arguments?.getBoolean("isFirst") == true) {
            CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {
                (requireActivity() as MainActivity).getDataStore()?.edit {
                    it[intPreferencesKey(Constants.BREAKFAST_Hour)] = 9
                    it[intPreferencesKey(Constants.BREAKFAST_min)] = 30

                    it[intPreferencesKey(Constants.LUNCH_Hour)] = 13
                    it[intPreferencesKey(Constants.LUNCH_min)] = 30

                    it[intPreferencesKey(Constants.DINNER_Hour)] = 20
                    it[intPreferencesKey(Constants.DINNER_min)] = 30

                    it[intPreferencesKey(Constants.EVENING_Hour)] = 17
                    it[intPreferencesKey(Constants.EVENING_Min)] = 30

                }

            }

        }

        CoroutineScope(Dispatchers.IO).launch(Dispatchers.Main) {
            (requireActivity() as MainActivity).getDataStore()?.data?.first()?.also {
                breakfastTime.text = ("BreakFast ${
                    it.get(intPreferencesKey(Constants.BREAKFAST_Hour)) ?: 9.hoursToAM_PM(
                        it.get(
                            intPreferencesKey(
                                Constants.BREAKFAST_min
                            )
                        ) ?: 30
                    )
                }")
                lunchTime.text = ("Lunch ${
                    it.get(intPreferencesKey(Constants.LUNCH_Hour)) ?: 13.hoursToAM_PM(
                        it.get(
                            intPreferencesKey(
                                Constants.LUNCH_min
                            )
                        ) ?: 30
                    )
                }")
                dinerTime.text = ("Dinner ${
                    it.get(intPreferencesKey(Constants.DINNER_Hour)) ?: 20.hoursToAM_PM(
                        it.get(
                            intPreferencesKey(
                                Constants.DINNER_min
                            )
                        ) ?: 30
                    )
                }")
                eveningTime.text = ("Evening ${
                    it.get(intPreferencesKey(Constants.EVENING_Hour)) ?: 17.hoursToAM_PM(
                        it.get(
                            intPreferencesKey(
                                Constants.EVENING_Min
                            )
                        ) ?: 30
                    )
                }")
                snoozeLabel.text =
                    ("Snooze By ${it.get(intPreferencesKey(Constants.SNOOZETIME)) ?: 5} Minutes")
            }
        }





        breakfastEdit.setOnClickListener {
            val calendar = Calendar.getInstance()
            val timePickerDialog = TimePickerDialog(
                requireContext(),
                { view, hourOfDay, minute ->
                    breakfastTime.text = ("BreakFast alarm time ${hourOfDay.hoursToAM_PM(minute)}")
                    CoroutineScope(Dispatchers.IO).launch {
                        (requireActivity() as MainActivity).getDataStore()?.edit {
                            it[intPreferencesKey(Constants.BREAKFAST_Hour)] = hourOfDay
                            it[intPreferencesKey(Constants.BREAKFAST_min)] = minute


                        }

                    }
                }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), false
            )

            timePickerDialog.show()
        }
        lunchEdit.setOnClickListener {
            val calendar = Calendar.getInstance()
            val timePickerDialog = TimePickerDialog(
                requireContext(),
                { _, hourOfDay, minute ->
                    lunchTime.text = ("Lunch ${hourOfDay.hoursToAM_PM(minute)}")
                    CoroutineScope(Dispatchers.IO).launch {

                        (requireActivity() as MainActivity).getDataStore()?.edit {
                            it[intPreferencesKey(Constants.LUNCH_Hour)] = hourOfDay
                            it[intPreferencesKey(Constants.LUNCH_min)] = minute

                        }

                    }
                }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), false
            )

            timePickerDialog.show()
        }
        dinnerEdit.setOnClickListener {
            val calendar = Calendar.getInstance()
            val timePickerDialog = TimePickerDialog(
                requireContext(),
                { view, hourOfDay, minute ->
                    dinerTime.text = ("Dinner ${hourOfDay.hoursToAM_PM(minute)}")
                    CoroutineScope(Dispatchers.IO).launch {
                        (requireActivity() as MainActivity).getDataStore()?.edit {
                            it[intPreferencesKey(Constants.DINNER_Hour)] = hourOfDay
                            it[intPreferencesKey(Constants.DINNER_min)] = minute


                        }

                    }
                }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), false
            )

            timePickerDialog.show()
        }
        eveningEdit.setOnClickListener {
            val calendar = Calendar.getInstance()
            val timePickerDialog = TimePickerDialog(
                requireContext(),
                { view, hourOfDay, minute ->
                    eveningTime.text = ("Evening ${hourOfDay.hoursToAM_PM(minute)}")
                    CoroutineScope(Dispatchers.IO).launch {
                        (requireActivity() as MainActivity).getDataStore()?.edit {
                            it[intPreferencesKey(Constants.EVENING_Hour)] = hourOfDay
                            it[intPreferencesKey(Constants.EVENING_Min)] = minute
                        }

                    }
                }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), false
            )

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
                if (it.getPreferences()?.getBoolean(Constants.ISLoggedIN, true) == true) {
                    it.removePreferenceFrag(this)
                }
            }

            (requireActivity() as MainActivity).also {
                it.getPreferences()?.edit()?.putBoolean(Constants.ISLoggedIN, true)?.apply()
                it.removeFrag(this)
                it.showTabsView()
            }
//            requireActivity().also {
//                if(arguments?.getBoolean("isFirst") == true) {
//                    it.startService(
//                        Intent(it, MedService::class.java).putExtra(
//                            Constants.callFOR,
//                            Constants.SYNC
//                        )
//                    )
//                }
//            }
        }

        when {
            (requireActivity() as MainActivity).sharedPreferences?.getString(
                Constants.LANG,
                "en"
            ) == "en" -> {
                eng.isChecked = true
            }
            (requireActivity() as MainActivity).sharedPreferences?.getString(
                Constants.LANG,
                "en"
            ) == "ta" -> {
                tamil.isChecked = true
            }
            (requireActivity() as MainActivity).sharedPreferences?.getString(
                Constants.LANG,
                "en"
            ) == "hi" -> {
                hindi.isChecked = true
            }
        }
        langGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.eng) {
                (requireActivity() as MainActivity).setLang("en")
            } else if (checkedId == R.id.hindi) {
                (requireActivity() as MainActivity).setLang("hi")

            } else {
                (requireActivity() as MainActivity).setLang("ta")
            }
        }

    }
    

    private fun showNumberPicker() {
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
            CoroutineScope(Dispatchers.IO).launch {
                (requireActivity() as MainActivity).getDataStore()?.edit {
                    it[intPreferencesKey(Constants.SNOOZETIME)] = i1
                }
            }
        }
        d.setPositiveButton(
            "Done"
        ) { _, i ->
            alertDialog.dismiss()
        }
        d.setNegativeButton(
            "Cancel"
        ) { _, i ->
            alertDialog.dismiss()
        }

        alertDialog.show()
    }


}
