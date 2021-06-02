package com.kani.medzone.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kani.medzone.R
import com.kani.medzone.db.Reading
import com.kani.medzone.db.Tablets

/**Created by Guru kathir.J on 02,June,2021 **/
class ReadingAdapter(private var readingList: ArrayList<Reading>): RecyclerView.Adapter<ReadingAdapter.ReadingsHolder>() {


    class ReadingsHolder(v: View): RecyclerView.ViewHolder(v) {

        val label = v.findViewById<EditText>(R.id.readlingLabelET)
        val value = v.findViewById<EditText>(R.id.readlingValueET)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReadingsHolder {
        return ReadingsHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.reading_item, parent, false))
    }

    override fun onBindViewHolder(holder: ReadingsHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return readingList.size
    }

    fun setData(it: ArrayList<Reading>?) {

        if (it != null) {
            readingList =it
        }

        notifyDataSetChanged()
    }


}