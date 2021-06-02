package com.kani.medzone.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.kani.medzone.R

/**Created by Guru kathir.J on 22,May,2021 **/


class ImageDialog : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout to use as dialog or embedded fragment
        return inflater.inflate(R.layout.full_image_dialog, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            // Create the AlertDialog object and return it
            builder.create().also {
                it.setCanceledOnTouchOutside(true)
                it.setCancelable(true)
            }
        } ?: throw IllegalStateException("Activity cannot be null")
    }


    }
