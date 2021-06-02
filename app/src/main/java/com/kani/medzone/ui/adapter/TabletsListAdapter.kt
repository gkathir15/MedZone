package com.kani.medzone.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kani.medzone.ItemClickListener
import com.kani.medzone.R
import com.kani.medzone.db.Tablets

/**
 *Created by Guru kathir.J on 08,May,2021*
 **/
class TabletsListAdapter(private var tabletsList:ArrayList<Tablets>,
                         private val listener:ItemClickListener):RecyclerView.Adapter<TabletsListAdapter.TabletViewHolder>() {


    class TabletViewHolder(v: View): RecyclerView.ViewHolder(v) {

        val tabImageView = v.findViewById<ImageView>(R.id.drugImg)
        val tv_drugName = v.findViewById<TextView>(R.id.tv_drugName)
        val mg = v.findViewById<TextView>(R.id.mg)
        val availabletabs = v.findViewById<TextView>(R.id.availAbleNo)

        val instruction = v.findViewById<TextView>(R.id.tv_instruction)

        val breakfast = v.findViewById<CheckBox>(R.id.morning)
        val lunch = v.findViewById<CheckBox>(R.id.noon)
        val dinner = v.findViewById<CheckBox>(R.id.night)
        val evening = v.findViewById<CheckBox>(R.id.evening)

        val withfood = v.findViewById<RadioButton>(R.id.withfood)
        val afterFood = v.findViewById<RadioButton>(R.id.afterFood)
        val beforeFood = v.findViewById<RadioButton>(R.id.beforefood)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabletViewHolder {
    return TabletViewHolder(
        LayoutInflater.from(parent.context)
        .inflate(R.layout.tablet_list_row, parent, false))
    }

    override fun onBindViewHolder(holder: TabletViewHolder, position: Int) {
            Glide.with(holder.itemView.context).load(tabletsList[position].imageUrl).into(holder.tabImageView)
        holder.tabImageView.setOnClickListener {
            listener.expandImageClicked(tabletsList[position].imageUrl)
        }
            holder.tv_drugName.text = tabletsList[position].name
            holder.mg.text = ("${tabletsList[position].mgDosage} Mg")
        holder.mg.text = ("${tabletsList[position].available} Available" )
        holder.availabletabs.text =tabletsList[position].available.toString()
        when(tabletsList[position].mealDosage)
        {
            1-> holder.beforeFood.isChecked = true
            2-> holder.withfood.isChecked = true
            3-> holder.afterFood.isChecked = true
        }
        if (tabletsList[position].noon==1)
            holder.lunch.isChecked = true

        if (tabletsList[position].morning==1)
            holder.breakfast.isChecked = true

        if (tabletsList[position].night==1)
            holder.dinner.isChecked = true
        if (tabletsList[position].evening==1)
            holder.evening.isChecked = true
    }

    override fun getItemCount(): Int {
      return tabletsList.size
    }

    fun setData(it: ArrayList<Tablets>?) {
        if (it != null) {
            tabletsList =it
        }
        notifyDataSetChanged()
    }


}