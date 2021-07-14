package com.kani.medzone.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.datastore.preferences.core.Preferences
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kani.medzone.vm.ActivityViewModel
import com.kani.medzone.ItemClickListener
import com.kani.medzone.MainActivity
import com.kani.medzone.R
import com.kani.medzone.db.Tablets
import com.kani.medzone.ui.adapter.TabletsListAdapter
import kotlinx.android.synthetic.main.fragment_tablet_list.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class TabletListFragment : Fragment(),ItemClickListener {

    private var tabletAdapter: TabletsListAdapter? = null
    private val homeViewModel by activityViewModels<ActivityViewModel>()
    var pref:Preferences?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tablet_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch(IO) {
            pref = (requireActivity() as MainActivity).getDataStore()?.data?.first()?.toPreferences()
        }
        addTablet.setOnClickListener {
            childFragmentManager.beginTransaction().add(R.id.root, AddTabletsFragment()).commitNow()
            addTablet.visibility = GONE
        }
        tabletAdapter = TabletsListAdapter(ArrayList<Tablets>(0),this)

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
        GlobalScope.launch {
            homeViewModel.fetchTabletEntry()
        }
        addTablet.visibility = VISIBLE
    }

    override fun expandImageClicked(url: ByteArray?) {
        (requireActivity() as MainActivity).showNoticeDialog(url)

    }

    override fun takeTabletsClicked(btnType: String) {
        
    }

}