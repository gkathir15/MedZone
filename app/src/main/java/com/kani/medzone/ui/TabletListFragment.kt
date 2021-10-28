package com.kani.medzone.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.datastore.preferences.core.Preferences
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentSnapshot
import com.kani.medzone.ItemClickListener
import com.kani.medzone.MainActivity
import com.kani.medzone.R
import com.kani.medzone.db.TabletEntry
import com.kani.medzone.db.Tablets
import com.kani.medzone.ui.adapter.TabletsListAdapter
import com.kani.medzone.vm.ActivityViewModel
import kotlinx.android.synthetic.main.fragment_tablet_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class TabletListFragment : Fragment(), ItemClickListener {

    private var tabletAdapter: TabletsListAdapter? = null
    private val homeViewModel by activityViewModels<ActivityViewModel>()
    var pref: Preferences? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tablet_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(IO).launch(IO) {
            pref =
                (requireActivity() as MainActivity).getDataStore()?.data?.first()?.toPreferences()
        }
        addTablet.setOnClickListener {
            childFragmentManager.beginTransaction().add(R.id.root, AddTabletsFragment()).commitNow()
            addTablet.visibility = GONE


        }
        tabletAdapter = TabletsListAdapter(ArrayList<Tablets>(0), this)

        homeViewModel.tabletsList.observe(viewLifecycleOwner, {
            tabletAdapter?.setData(it)
        })

        tabletsList.also {
            it.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            it.adapter = tabletAdapter
        }


    }

    fun back(addTabletsFragment: AddTabletsFragment) {
        childFragmentManager.beginTransaction().remove(addTabletsFragment).commit()
        CoroutineScope(Dispatchers.IO).launch {
            homeViewModel.fetchTabletEntry()
        }
        addTablet.visibility = VISIBLE
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

    override fun deleteTab(tab: Tablets, pos: Int) {

        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Are you sure Want to delete the tablet ?")
            builder.apply {
                setPositiveButton(R.string.ok,
                    DialogInterface.OnClickListener { dialog, id ->
                        // User clicked OK button
                        CoroutineScope(IO).launch {
                            homeViewModel.databaseInstance().tabletsDao().delete(tab)
                            homeViewModel.databaseInstance().tabEntryDao().getAll().filter { it.tablet.tabletid == tab.tabletid }
                                .forEach {
                                    homeViewModel.databaseInstance().tabEntryDao().delete(it)
                                }
                            homeViewModel.fetchTabletsList()
                            homeViewModel.fetchTabletEntry()
                        }


                    })
                setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        // User cancelled the dialog
                        dialog.dismiss()
                    })
            }
            // Set other dialog properties


            // Create the AlertDialog
            builder.create()
        }
        alertDialog?.show()


    }

    override fun personSelected(snap: DocumentSnapshot) {
        TODO("Not yet implemented")
    }


}