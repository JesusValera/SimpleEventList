package com.rad4m.jesusreales.simpleeventlist.events.fragment

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rad4m.jesusreales.simpleeventlist.events.MainActivity
import com.rad4m.jesusreales.simpleeventlist.R
import com.rad4m.jesusreales.simpleeventlist.data.model.CellElement
import java.util.*
import kotlin.collections.ArrayList

class PastEventsFragment : BaseFragment() {

    companion object {
        fun newInstance(): PastEventsFragment = PastEventsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_with_events, container, false)

        recyclerView = view.findViewById(R.id.rvEvents)
        recyclerView.setHasFixedSize(true)

        recreateAdapter(view)

        val swipeRefresh: SwipeRefreshLayout = view.findViewById(R.id.swipe)
        swipeRefresh.setOnRefreshListener {
            recreateAdapter(view)

            if (swipeRefresh.isRefreshing) {
                swipeRefresh.isRefreshing = false
            }
        }

        return view
    }

    override fun filterEvents(): ArrayList<CellElement> {
        val activity = activity as MainActivity
        val cells = activity.cellElements
        val cellFilter = arrayListOf<CellElement>()
        cells.sortDescending()

        for (i in cells.indices) {
            if (DateUtils.isToday(cells[i].event!!.date.time)) {
                cellFilter.add(cells[i])
                continue
            }
            if (cells[i].event!!.date.before(Date(System.currentTimeMillis()))) {
                cellFilter.add(cells[i])
            }
        }

        return cellFilter
    }

}