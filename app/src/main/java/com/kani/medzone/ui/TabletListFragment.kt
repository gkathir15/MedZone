package com.kani.medzone.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kani.medzone.ActivityViewModel
import com.kani.medzone.R
import com.kani.medzone.db.Tablets
import kotlinx.android.synthetic.main.fragment_tablet_list.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class TabletListFragment : Fragment() {

    private var tabletAdapter: TabletsListAdapter? = null
    private val homeViewModel by activityViewModels<ActivityViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tablet_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addTablet.setOnClickListener {
            childFragmentManager.beginTransaction().add(R.id.root, AddTabletsFragment()).commit()
        }
        tabletAdapter = TabletsListAdapter(ArrayList<Tablets>(0))
        GlobalScope.launch {
            homeViewModel.fetchTabletsList()
        }
        homeViewModel.tabletsList?.observe(viewLifecycleOwner, {
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
            homeViewModel.fetchTabletsList()
        }
    }

}