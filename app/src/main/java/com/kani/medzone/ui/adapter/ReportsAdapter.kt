package com.kani.medzone.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kani.medzone.R
import com.kani.medzone.db.Report
import com.kani.medzone.ui.adapter.ReportsAdapter.ReportsHolder
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**Created by Guru kathir.J on 05,June,2021 **/
class ReportsAdapter(private var readingList: ArrayList<Report>, private val ctx: Context) :
    RecyclerView.Adapter<ReportsHolder>() {

    class ReportsHolder(v: View) : RecyclerView.ViewHolder(v) {
        val readingsRecyclerView = v.findViewById<RecyclerView>(R.id.readings)
        val takenIn = v.findViewById<TextView>(R.id.takenOn)
        val nameV = v.findViewById<TextView>(R.id.Label)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportsHolder {
        return ReportsHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.report_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ReportsHolder, position: Int) {
        holder.readingsRecyclerView.also {
            it.layoutManager = LinearLayoutManager(ctx, RecyclerView.VERTICAL, false)
            it.adapter = ReadingListAdapter(readingList[position].readingList)
        }
        holder.nameV.text = readingList[position].name
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        holder.takenIn.text = sdf.format(Date(readingList[position].takenOn))
    }

    override fun getItemCount(): Int {
        return readingList.size
    }

    fun updateList(it: java.util.ArrayList<Report>?) {
        if (it != null) {
            readingList = it
            notifyDataSetChanged()
        }
    }
}