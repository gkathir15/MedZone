package com.kani.medzone.ui.adapter

import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kani.medzone.Constants
import com.kani.medzone.R
import kotlinx.android.synthetic.main.admin_detail_layout.view.*

/**Created by Guru kathir.J on 31,August,2021 **/
class AdminDetailAdapter(private val docs: HashMap<String, Any>) :
    RecyclerView.Adapter<AdminDetailAdapter.DocsHolder>() {
    class DocsHolder(v: View) : RecyclerView.ViewHolder(v) {
        val date = v.date

        val dur1 = v.dur1 as TextView
        val data1 = v.data1 as TextView

        val dur2 = v.dur2 as TextView
        val data2 = v.data2 as TextView

        val dur3 = v.dur3 as TextView
        val data3 = v.data3 as TextView

        val dur4 = v.dur4 as TextView
        val data4 = v.data4 as TextView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocsHolder {
        return DocsHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.admin_detail_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DocsHolder, position: Int) {
        val key = docs.keys.toList()[position]
        holder.date.text = key

        val hashmap = docs[key] as HashMap<String, Any>

        if (hashmap.containsKey(Constants.BREAKFAST)) {
            val tabmap = hashmap[Constants.BREAKFAST] as HashMap<String, Any>

            holder.dur1.text = Constants.BREAKFAST

            if (tabmap.containsKey(Constants.TAKE)) {

                val taken = tabmap[Constants.TAKE] as ArrayList<String>

                if (taken.isNotEmpty()) {
                    val span = SpannableString(Constants.TAKE)
                    span.setSpan(
                        RelativeSizeSpan(1.5f),
                        0,
                        span.length - 1,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    holder.data1.append(("\n \n ${span} \n \n"))
                    holder.data1.append(taken.joinToString(separator = "\n"))
                }
            }
            if (tabmap.containsKey(Constants.SKIP)) {
                val skips = tabmap[Constants.SKIP] as ArrayList<String>

                val span = SpannableString(Constants.SKIP)
                span.setSpan(
                    RelativeSizeSpan(1.5f),
                    0,
                    span.length - 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                if (skips.isNotEmpty()) {
                    holder.data1.append(("\n \n ${Constants.SKIP} \n \n"))
                    holder.data1.append(skips.joinToString(separator = "\n"))
                }
            }
        }

        if (hashmap.containsKey(Constants.LUNCH)) {
            val tabmap = hashmap[Constants.LUNCH] as HashMap<String, Any>

            holder.dur2.text = Constants.LUNCH

            if (tabmap.containsKey(Constants.TAKE)) {

                val taken = tabmap[Constants.TAKE] as ArrayList<String>

                if (taken.isNotEmpty()) {
                    val span = SpannableString(Constants.TAKE)
                    span.setSpan(
                        RelativeSizeSpan(1.5f),
                        0,
                        span.length - 1,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    holder.data2.append(("\n \n ${span} \n \n"))
                    holder.data2.append(taken.joinToString(separator = "\n"))
                }
            }
            if (tabmap.containsKey(Constants.SKIP)) {
                val skips = tabmap[Constants.SKIP] as ArrayList<String>

                if (skips.isNotEmpty()) {
                    val span = SpannableString(Constants.SKIP)
                    span.setSpan(
                        RelativeSizeSpan(1.5f),
                        0,
                        span.length - 1,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    holder.data2.append(("\n \n ${span} \n \n"))
                    holder.data2.append(skips.joinToString(separator = "\n"))
                }
            }
        }

        if (hashmap.containsKey(Constants.EVENING)) {
            val tabmap = hashmap[Constants.EVENING] as HashMap<String, Any>

            holder.dur3.text = Constants.EVENING

            if (tabmap.containsKey(Constants.TAKE)) {

                val taken = tabmap[Constants.TAKE] as ArrayList<String>

                if (taken.isNotEmpty()) {
                    val span = SpannableString(Constants.TAKE)
                    span.setSpan(
                        RelativeSizeSpan(1.5f),
                        0,
                        span.length - 1,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    holder.data3.append(("\n \n ${span} \n \n"))
                    holder.data3.append(taken.joinToString(separator = "\n"))
                }
            }
            if (tabmap.containsKey(Constants.SKIP)) {
                val skips = tabmap[Constants.SKIP] as ArrayList<String>

                if (skips.isNotEmpty()) {
                    val span = SpannableString(Constants.SKIP)
                    span.setSpan(
                        RelativeSizeSpan(1.5f),
                        0,
                        span.length - 1,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )

                    holder.data3.append(("\n \n ${span} \n \n"))
                    holder.data3.append(skips.joinToString(separator = "\n"))
                }
            }
        }

        if (hashmap.containsKey(Constants.DINNER)) {
            val tabmap = hashmap[Constants.DINNER] as HashMap<String, Any>

            holder.dur4.text = Constants.DINNER

            if (tabmap.containsKey(Constants.TAKE)) {

                val taken = tabmap[Constants.TAKE] as ArrayList<String>

                if (taken.isNotEmpty()) {
                    val span = SpannableString(Constants.TAKE)
                    span.setSpan(
                        RelativeSizeSpan(1.5f),
                        0,
                        span.length - 1,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )

                    holder.data4.append(("\n \n ${span} \n \n"))
                    holder.data4.append(taken.joinToString(separator = "\n"))
                }
            }
            if (tabmap.containsKey(Constants.SKIP)) {
                val skips = tabmap[Constants.SKIP] as ArrayList<String>

                if (skips.isNotEmpty()) {
                    val span = SpannableString(Constants.TAKE)
                    span.setSpan(
                        RelativeSizeSpan(1.5f),
                        0,
                        span.length - 1,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )

                    holder.data4.append(("\n \n ${span} \n \n"))
                    holder.data4.append(skips.joinToString(separator = "\n"))
                }
            }
        }


    }

    override fun getItemCount(): Int {
        return docs.size
    }

}