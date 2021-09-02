package com.kani.medzone.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentSnapshot
import com.kani.medzone.Constants
import com.kani.medzone.ItemClickListener
import com.kani.medzone.R
import kotlinx.android.synthetic.main.users_item.view.*

/**Created by Guru kathir.J on 23,August,2021 **/
class DocumentAdapter(private val docs: List<DocumentSnapshot>, val lsitener: ItemClickListener) :
    RecyclerView.Adapter<DocumentAdapter.DocsHolder>() {

    class DocsHolder(v: View) : RecyclerView.ViewHolder(v) {
        val name = v.userName
        val phNum = v.phNo
        val illness = v.illness
        val patientNo = v.patientNo

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocsHolder {
        return DocsHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.users_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DocsHolder, position: Int) {
        holder.illness.text =
            "Illness:${docs[holder.adapterPosition][Constants.ILLNESS].toString()}"

        holder.name.text = "Name${docs[holder.adapterPosition][Constants.NAME].toString()}"
        holder.phNum.text =
            "Ph no${docs[holder.adapterPosition][Constants.PHONE_NUMBER].toString()}"
        holder.patientNo.text =
            "Patient Id${docs[holder.adapterPosition][Constants.PATIENT_NO].toString()}"
        holder.itemView.setOnClickListener{
            lsitener.personSelected(docs[position])
        }
    }

    override fun getItemCount(): Int {
        return docs.size
    }
}