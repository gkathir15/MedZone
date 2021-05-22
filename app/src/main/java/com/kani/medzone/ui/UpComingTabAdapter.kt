package com.kani.medzone.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kani.medzone.Constants
import com.kani.medzone.ItemClickListener
import com.kani.medzone.R
import com.kani.medzone.db.TabletEntry
import kotlinx.android.synthetic.main.take_tablets_item.view.*

/**Created by Guru kathir.J on 22,May,2021 **/
class UpComingTabAdapter(val dataList:ArrayList<TabletEntry>,val listener: ItemClickListener): RecyclerView.Adapter<UpComingTabAdapter.Holder>() {

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tabImage = itemView.tabletImage
        val tabname = itemView.tabletName
        val tabletmg = itemView.tabletmg
        val instruction = itemView.instruction
        val foodPref = itemView.foodPref

        val takeV = itemView.take
        val snoozeV = itemView.snooze
        val skipV = itemView.skip

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.take_tablets_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.tabImage.setOnClickListener {
            listener.expandImageClicked(dataList[position].tablet.imageUrl)
        }
        holder.tabname.text = dataList[position].tablet.name
        holder.instruction.text = dataList[position].tablet.Instruction
        holder.foodPref.text = dataList[position].tablet.mealDosage.toString()
        holder.tabletmg.text = ("${dataList[position].tablet.mgDosage} mg")

        holder.takeV.setOnClickListener {
            listener.takeTabletsClicked(Constants.TAKE)
        }
        holder.snoozeV.setOnClickListener {
            listener.takeTabletsClicked(Constants.SNOOZE)
        }
        holder.skipV.setOnClickListener {
            listener.takeTabletsClicked(Constants.  SKIP)
        }

    }

    override fun getItemCount(): Int {
       return dataList.size
    }
}