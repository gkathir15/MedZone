package com.kani.medzone.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isNotEmpty
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.DocumentSnapshot
import com.kani.medzone.Constants
import com.kani.medzone.ItemClickListener
import com.kani.medzone.MainActivity
import com.kani.medzone.R
import com.kani.medzone.db.TabletEntry
import com.kani.medzone.ui.adapter.AdminDetailAdapter
import com.kani.medzone.ui.adapter.DocumentAdapter
import com.kani.medzone.vm.ActivityViewModel
import kotlinx.android.synthetic.main.activity_admin.*


class History : Fragment() , ItemClickListener {

    private val homeViewModel by activityViewModels<ActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ref = homeViewModel.firestore.collection(Constants.users)

        ref.get().addOnCompleteListener {

            val docs = it.result?.documents?.filter {
                it[Constants.PHONE_NUMBER] == (requireActivity() as MainActivity).sharedPreferences?.getString(
                    Constants.PHONE_NUMBER,
                    ""
                )
            }
            docs?.also { it1->
                userRecycler.also {
                    it.layoutManager = LinearLayoutManager(requireContext())
                    val tabs = (it1.get(0).get(Constants.TABLETS) as HashMap<String, Any>)
                    if(tabs.isNotEmpty() )
                    it.adapter =  AdminDetailAdapter( tabs)
                }
            }


        }


    }

    override fun expandImageClicked(url: ByteArray?) {
        TODO("Not yet implemented")
    }

    override fun takeTabletsClicked(btnType: String) {
        TODO("Not yet implemented")
    }

    override fun takeTablet(tab: TabletEntry, pos: Int) {
        TODO("Not yet implemented")
    }

    override fun skipTablet(tab: TabletEntry, pos: Int) {
        TODO("Not yet implemented")
    }

    override fun personSelected(snap: DocumentSnapshot) {
        TODO("Not yet implemented")
    }
}