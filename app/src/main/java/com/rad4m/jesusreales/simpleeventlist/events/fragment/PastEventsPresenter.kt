package com.rad4m.jesusreales.simpleeventlist.events.fragment

import android.text.format.DateUtils
import com.rad4m.jesusreales.simpleeventlist.data.model.CellElement
import com.rad4m.jesusreales.simpleeventlist.events.MainActivity
import java.util.*

class PastEventsPresenter(val baseFragment: FragmentEventsContract.View) : FragmentEventsContract.Presenter {

    init {
        baseFragment.mPresenter = this
    }

    override fun filterEvents(activity: MainActivity): ArrayList<CellElement> {
        val cells = activity.cellElements
        val cellFilter = arrayListOf<CellElement>()
        cells.sortDescending()

        for (i in cells.indices) {
            if (DateUtils.isToday(cells[i].event!!.date!!.time)) {
                cellFilter.add(cells[i])
                continue
            }
            if (cells[i].event!!.date!!.before(Date(System.currentTimeMillis()))) {
                cellFilter.add(cells[i])
            }
        }

        //baseFragment.setEventsIntoRecyclerView(cellFilter)
        return cellFilter
    }

}