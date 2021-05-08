package com.kani.medzone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kani.medzone.ui.TabletListFragment
import com.kani.medzone.ui.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var pageAdapter: ViewPagerAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pageAdapter = ViewPagerAdapter(supportFragmentManager)


        pageAdapter?.addFragment("TABLETS1", TabletListFragment())
        pageAdapter?.addFragment("TABLETS2", TabletListFragment())
        pageAdapter?.addFragment("TABLETS", TabletListFragment())


        pager.adapter = pageAdapter


    }


}