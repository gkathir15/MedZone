package com.kani.medzone.ui

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.kani.medzone.Constants
import com.kani.medzone.MainActivity
import com.kani.medzone.R
import com.kani.medzone.db.TabletEntry
import com.kani.medzone.db.Tablets
import com.kani.medzone.vm.ActivityViewModel
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.android.synthetic.main.calendar_select_layout.view.*
import kotlinx.android.synthetic.main.fragment_add_tablets.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


class AddTabletsFragment : Fragment() {
    private val homeViewModel by activityViewModels<ActivityViewModel>()
    private var imgFile: File? = null
    private val tablet = Tablets(
        0, "", "0", 1, 0, null, 1, 0, 0, 0, 0, ""
    )

    private var selectedDay: CalendarDay? = null
    //private var datastore: Preferences? = null
    // At the top level of your kotlin file:
   // val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    var hr =0
    var min =0
    var datastore:Preferences?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_tablets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        datastore = (parentFragment as TabletListFragment).pref

        tabletImg.setOnClickListener {
            ImagePicker.with(this)
                .cropSquare() //Crop image(Optional), Check Customization for more option
                .compress(512)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(
                    1080,
                    1080
                )    //Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        }

        foodPref.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.beforeFood -> {
                    tablet.mealDosage = 1
                }
                R.id.withFood -> {
                    tablet.mealDosage = 2
                }
                R.id.afterFood -> {
                    tablet.mealDosage = 3
                }
            }
        }

        add.setOnClickListener {

            if (imgFile != null) {
                tablet.imageUrl = imgFile?.readBytes()
            }
            if (tabletNameET.text.toString().isNotBlank()) {
                tablet.name = tabletNameET.text.toString()
                if (mgEt.text.toString().isNotBlank()) {
                    if (qtyEt.text.toString().isNotBlank()) {
                        tablet.mgDosage = mgEt.text.toString()
                        tablet.qty = qtyEt.text.toString().toInt()
                        if (instructionEt.text.toString().isNotBlank())
                            tablet.instruction = instructionEt.text.toString()



                        showCalendarSelectionDialog()


                    } else {
                        ed_qty.error = "Enter Available No"
                    }
                } else {
                    ed_mg.error = Constants.ENTER_DOSAGE_SIZE
                }
            } else {
                ed_tabletName.error = Constants.ENTER_TABLET_NAME
            }


        }

        morning.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                tablet.morning = 1
            else
                tablet.morning = 0
        }
        noon.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                tablet.noon = 1
            else
                tablet.noon = 0
        }
        night.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                tablet.night = 1
            else
                tablet.night = 0
        }
        evening.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                tablet.evening = 1
            else
                tablet.evening = 0
        }







    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data
                print(fileUri)

                //You can get File object from intent
                imgFile = ImagePicker.getFile(data)!!

                Glide.with(requireContext()).load(imgFile).into(tabletImg)
            }
            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(activity, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(activity, Constants.TASK_CANCELLED, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showCalendarSelectionDialog() {
        val builder = Dialog(requireContext())
        builder.setCanceledOnTouchOutside(true)
        builder.setCanceledOnTouchOutside(true)
        val inflater = layoutInflater

        val dialogLayout: View = inflater.inflate(R.layout.calendar_select_layout, null)
        dialogLayout.done.isEnabled = false
        dialogLayout.calendarView.also {
            it.setAllowClickDaysOutsideCurrentMonth(false)
            it.setOnDateChangedListener { _, date, _ ->
                selectedDay = date
                dialogLayout.done.isEnabled = true
            }
        }
        dialogLayout.done.setOnClickListener {
            if (selectedDay != null) {
                GlobalScope.launch {
                    homeViewModel.run {
                        this.databaseInstance().tabletsDao().insert(tablet)
                        this.fetchTabletsList()
                        this.databaseInstance().tabEntryDao()
                            .insertAll(getTabletEntryList(tablet))
                    }

                }
                (parentFragment as TabletListFragment).back(this)
            }
            builder.cancel()
        }
        builder.setContentView(dialogLayout)

        builder.show()
    }

    private fun getTabletEntryList(tablet: Tablets): ArrayList<TabletEntry> {

        val list = ArrayList<TabletEntry>()


        datastore?.also {
            var qty = tablet.qty
            var iteration = 0

            while (qty != 0) {
                if (tablet.morning == 1 && qty != 0) {
                    val date = Calendar.getInstance().also { itx ->
                        itx.set(
                            selectedDay!!.year, selectedDay!!.month, selectedDay!!.day,
                            it[intPreferencesKey(Constants.BREAKFAST_Hour)]!!,
                            it[intPreferencesKey(Constants.BREAKFAST_min)]!!, 0
                        )
                    }
                    date.add(Calendar.DAY_OF_MONTH, iteration)
                    list.add(
                        TabletEntry(
                            "${tablet.tabletid}_${date.timeInMillis}",
                            date.timeInMillis,
                            0,
                            tablet,1
                        )
                    )

                    qty = qty?.minus(1)

                }
                if (tablet.noon == 1 && qty != 0) {
                    val date = Calendar.getInstance().also { itx ->
                        itx.set(
                            selectedDay!!.year, selectedDay!!.month, selectedDay!!.day,
                            it[intPreferencesKey(Constants.LUNCH_Hour)]!!,
                            it[intPreferencesKey(Constants.LUNCH_min)]!!, 0
                        )
                    }
                    date.add(Calendar.DAY_OF_MONTH, iteration)
                    list.add(
                        TabletEntry(
                            "${tablet.tabletid}_${date.timeInMillis}",
                            date.timeInMillis,
                            0,
                            tablet,2
                        )
                    )

                    qty = qty?.minus(1)

                }
                if (tablet.evening == 1 && qty != 0) {
                    val date = Calendar.getInstance().also { itx ->
                        itx.set(
                            selectedDay!!.year, selectedDay!!.month, selectedDay!!.day,
                            it[intPreferencesKey(Constants.EVENING_Hour)]!!,
                            it[intPreferencesKey(Constants.EVENING_Min)]!!, 0
                        )
                    }
                    date.add(Calendar.DAY_OF_MONTH, iteration)
                    list.add(
                        TabletEntry(
                            "${tablet.tabletid}_${date.timeInMillis}",
                            date.timeInMillis,
                            0,
                            tablet,3
                        )
                    )

                    qty = qty?.minus(1)

                }
                if (tablet.night == 1 && qty != 0) {
                    val date = Calendar.getInstance().also { itx ->
                        itx.set(
                            selectedDay!!.year, selectedDay!!.month, selectedDay!!.day,
                            it[intPreferencesKey(Constants.DINNER_Hour)]!!,
                            it[intPreferencesKey(Constants.DINNER_min)]!!, 0
                        )
                    }
                    date.add(Calendar.DAY_OF_MONTH, iteration)
                    list.add(
                        TabletEntry(
                            "${tablet.tabletid}_${date.timeInMillis}",
                            date.timeInMillis,
                            0,
                            tablet,4
                        )
                    )

                    qty = qty?.minus(1)

                }


                iteration -= 1

            }

        }

        return list

    }


}