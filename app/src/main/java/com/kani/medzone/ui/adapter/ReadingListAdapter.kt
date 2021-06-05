package com.kani.medzone.ui.adapter

import android.text.Editable
import android.text.TextWatcher
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
class ReadingListAdapter(private var readingList: ArrayList<Reading>): RecyclerView.Adapter<ReadingListAdapter.ReadingsHolder>() {


    class ReadingsHolder(v: View): RecyclerView.ViewHolder(v) {

        val label = v.findViewById<TextView>(R.id.readlingLabel)
        val value = v.findViewById<TextView>(R.id.readingValue)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReadingsHolder {
        return ReadingsHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.reading_value_item, parent, false))
    }

    override fun onBindViewHolder(holder: ReadingsHolder, position: Int) {
        holder.label.text = readingList[position].name
        holder.value.text = readingList[position].read
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