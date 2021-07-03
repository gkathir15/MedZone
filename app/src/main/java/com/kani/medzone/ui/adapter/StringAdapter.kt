package com.kani.medzone.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kani.medzone.R
/**Created by Guru kathir.J on 27,June,2021 **/
class StringAdapter(val list:List<String>): RecyclerView.Adapter<StringAdapter.Holder>() {



    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val labelV: TextView = itemView as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return     Holder(
                LayoutInflater.from(parent.context)
                .inflate(R.layout.sting_layout,parent,false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.labelV.text = list[position]
    }

    override fun getItemCount(): Int {
     return   list.size
    }
}