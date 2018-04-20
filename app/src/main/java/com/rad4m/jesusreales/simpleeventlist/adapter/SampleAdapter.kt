package com.rad4m.jesusreales.simpleeventlist.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup
import com.rad4m.jesusreales.simpleeventlist.fragment.FutureEvents
import com.rad4m.jesusreales.simpleeventlist.fragment.PastEvents

class SampleAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    var mCurrentFragment: Fragment? = null

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        if (mCurrentFragment != `object`) {
            mCurrentFragment = `object` as Fragment
        }

        super.setPrimaryItem(container, position, `object`)
    }

    override fun getItem(position: Int): Fragment? = when (position) {
        0 -> PastEvents.newInstance()
        1 -> FutureEvents.newInstance()
        else -> null
    }

    override fun getPageTitle(position: Int): CharSequence = when (position) {
        0 -> "Past events"
        1 -> "Future events"
        else -> ""
    }

    override fun getCount(): Int = 2
}