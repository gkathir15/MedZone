package com.kani.medzone.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kani.medzone.ItemClickListener
import com.kani.medzone.R
import com.kani.medzone.db.TabletEntry
import com.kani.medzone.db.Tablets
import com.kani.medzone.ui.adapter.TabletsNotificationAdapter.Holder
import kotlinx.android.synthetic.main.tablet_frm_notification.view.*

/**Created by Guru kathir.J on 19,July,2021 **/
class TabletsNotificationAdapter(private var tabletsList:ArrayList<TabletEntry>,
private val listener: ItemClickListener):
    RecyclerView.Adapter<Holder>() {
    class Holder(v: View):RecyclerView.ViewHolder(v) {
        val name = v.name
        val image = v.image
        val instruction = v.instruction
        val foofPref = v.foodPref
        val take = v.take
        val skip = v.skip
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
      return   Holder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.tablet_frm_notification, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.name.text = tabletsList[position].tablet.name
        holder.instruction.text = tabletsList[position].tablet.instruction
        holder.foofPref.text = tabletsList[position].tablet.mealDosage.toString()
        when(tabletsList[position].tablet.mealDosage)
        {
            1-> holder.foofPref.text =  "before Food"
            2-> holder.foofPref.text ="with food"
            3-> holder.foofPref.text ="after Food"
        }
        Glide.with(holder.itemView.context).load(tabletsList[position].tablet.imageUrl).into(holder.image)
        holder.take.setOnClickListener {
            listener.takeTablet(tabletsList[position])
        }
        holder.skip.setOnClickListener {
            listener.skipTablet(tabletsList[position])
        }
    }

    override fun getItemCount(): Int {
    return  tabletsList.size
    }
}