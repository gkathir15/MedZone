package com.kani.medzone.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.firebase.firestore.DocumentSnapshot
import com.kani.medzone.vm.ActivityViewModel
import com.kani.medzone.ItemClickListener
import com.kani.medzone.MainActivity
import com.kani.medzone.R
import com.kani.medzone.db.TabletEntry

class DashboardFragment : Fragment(),ItemClickListener {

    private val homeViewModel by activityViewModels<ActivityViewModel>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        return root
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
        TODO("Not yet implemented")
    }


}