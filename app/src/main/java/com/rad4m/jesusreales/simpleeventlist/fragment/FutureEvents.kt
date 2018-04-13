package com.rad4m.jesusreales.simpleeventlist.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rad4m.jesusreales.simpleeventlist.R

class PastEvents : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_past_events, container, false)

    companion object {
        fun newInstance(): PastEvents = PastEvents()
    }
}