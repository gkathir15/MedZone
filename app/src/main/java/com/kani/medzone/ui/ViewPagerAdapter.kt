package com.kani.medzone.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**Created by Guru kathir.J on 07,May,2021 **/
class ViewPagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
){

    private val fragmentMap:LinkedHashMap<String, Fragment> = LinkedHashMap()
    override fun getCount(): Int {
        return fragmentMap.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentMap.values.toList()[position]
    }

    public fun addFragment(title:String,frag: Fragment)
    {
        fragmentMap[title] = frag
    }

    override fun getPageTitle(position: Int): CharSequence {
       // return super.getPageTitle(position)
        return fragmentMap.keys.toList()[position]
    }
}