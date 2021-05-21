package com.kani.medzone.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.kani.medzone.ActivityViewModel
import com.kani.medzone.Constants
import com.kani.medzone.R
import com.kani.medzone.db.Tablets
import kotlinx.android.synthetic.main.fragment_add_tablets.*
import kotlinx.android.synthetic.main.fragment_add_tablets.breakfast
import kotlinx.android.synthetic.main.fragment_add_tablets.dinner
import kotlinx.android.synthetic.main.fragment_add_tablets.lunch
import kotlinx.android.synthetic.main.tablet_list_row.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File


class AddTabletsFragment : Fragment() {
    private val homeViewModel by activityViewModels<ActivityViewModel>()
    private var imgFile: File? = null
    private val tablet = Tablets(0, "", 50, 1,0 ,ByteArray(0), 1, 0, 0)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_tablets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabletImg.setOnClickListener {
            ImagePicker.with(this)
                .cropSquare()
                .cameraOnly() //Crop image(Optional), Check Customization for more option
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
                    tablet.mealDosage = 3
                }
                R.id.afterFood -> {
                    tablet.mealDosage = 2
                }
            }
        }

        add.setOnClickListener {

            if (imgFile != null) {
                tablet.imageUrl = imgFile?.readBytes()
                if (tabletNameET.text.toString().isNotBlank()) {
                    tablet.name = tabletNameET.text.toString()
                    if (mgEt.text.toString().isNotBlank()) {
                        if(qtyEt.text.toString().isNotBlank()) {
                            tablet.mgDosage = mgEt.text.toString().toInt()
                            GlobalScope.launch {
                                homeViewModel.run {
                                    this.databaseInstance().tabletsDao().insert(tablet)
                                    this.fetchTabletsList()
                                }

                            }

                            (parentFragment as TabletListFragment).back(this)
                        }else{
                            ed_qty.error = "Enter Available No"
                        }
                    } else {
                        ed_mg.error = Constants.ENTER_DOSAGE_SIZE;
                    }
                } else {
                    ed_tabletName.error = Constants.ENTER_TABLET_NAME
                }
            } else {
                Toast.makeText(requireContext(), Constants.CAPTURE_IMAGE_OF_TABLET, Toast.LENGTH_SHORT)
                    .show()
            }


        }

        breakfast.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                tablet.breakfast = 1
            else
                tablet.breakfast = 0
        }
        lunch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                tablet.lunch = 1
            else
                tablet.lunch = 0
        }
        dinner.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                tablet.dinner = 1
            else
                tablet.dinner = 0
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

//                //You can also get File Path from intent
//                ImagePicker.getFilePath(data)!!
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


}