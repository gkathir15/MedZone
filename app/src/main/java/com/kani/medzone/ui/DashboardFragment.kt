package com.kani.medzone.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentSnapshot
import com.kani.medzone.Constants
import com.kani.medzone.vm.ActivityViewModel
import com.kani.medzone.ItemClickListener
import com.kani.medzone.MainActivity
import com.kani.medzone.R
import com.kani.medzone.db.TabletEntry
import com.kani.medzone.ui.adapter.TabletsNotificationAdapter
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.util.*
import kotlin.collections.ArrayList

class DashboardFragment : Fragment(),ItemClickListener {

    private val model by activityViewModels<ActivityViewModel>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var tabs = model.tabEntryList.value?.filter {
            val time = Calendar.getInstance()
            time.timeInMillis = it.date!!
            (
                    (time.get(Calendar.DATE)== Calendar.getInstance().get(Calendar.DATE)&&
                            time.get(Calendar.MONTH)==(Calendar.getInstance().get(Calendar.MONTH)+1)))
        } as ArrayList<TabletEntry>?
        model.tabEntryList.observe(viewLifecycleOwner,{
            tabs = it
            upcomingRecycler.adapter?.notifyDataSetChanged()
            snoozedRecycler.adapter?.notifyDataSetChanged()
        })
        upcomingRecycler.also {
                it.adapter =   TabletsNotificationAdapter(tabs?.filter { it.status ==0 }?.distinctBy { it.tablet.tabletid } as ArrayList<TabletEntry>, this)
            it.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
        }
        snoozedRecycler.also {
            it.adapter =   TabletsNotificationAdapter(tabs?.filter { it.status!=1&&it.status!=0 } ?.distinctBy { it.tablet.tabletid } as ArrayList<TabletEntry>, this)
            it.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
        }
    }

    override fun expandImageClicked(url: ByteArray?) {
        (requireActivity() as MainActivity).showNoticeDialog(url)
    }

    override fun takeTabletsClicked(btnType: String) {
    }

    override fun takeTablet(tab: TabletEntry, pos: Int) {
    }

    override fun skipTablet(tab: TabletEntry, pos: Int) {
    }

    override fun personSelected(snap: DocumentSnapshot) {
    }


}