package com.kani.medzone.ui

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.kani.medzone.R
import kotlinx.android.synthetic.main.full_image_dialog.view.*

/**Created by Guru kathir.J on 22,May,2021 **/


class ImageDialog : DialogFragment() {
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            // Inflate the layout to use as dialog or embedded fragment
            val view = inflater.inflate(R.layout.full_image_dialog, container, false)
            Glide.with(this).load(arguments?.getString("URL")).into(view.imageV)
        return view
        }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setMessage(R.string.close)
                .setPositiveButton(
                    R.string.close
                ) { dialog, id ->
                    dismiss()
                }
            builder.setCancelable(true)
            // Create the AlertDialog object and return it
            builder.create().also {
                it.setCanceledOnTouchOutside(true)
            }
        } ?: throw IllegalStateException("Activity cannot be null")
    }


    }
