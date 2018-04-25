package com.rad4m.jesusreales.simpleeventlist.events.fragment

import android.text.format.DateUtils
import com.rad4m.jesusreales.simpleeventlist.data.model.CellElement
import com.rad4m.jesusreales.simpleeventlist.events.MainActivity
import java.util.*

class FutureEventsPresenter(val baseFragment: FragmentEventsContract.View) : FragmentEventsContract.Presenter {

    init {
        baseFragment.mPresenter = this
    }

    override fun filterEvents(activity: MainActivity): ArrayList<CellElement> {
        val cellFilter = arrayListOf<CellElement>()
        val cells = activity.cellElements
        cells.sort()

        for (i in cells.indices) {
            if (DateUtils.isToday(cells[i].event!!.date!!.time)) {
                cellFilter.add(cells[i])
                continue
            }
            if (cells[i].event!!.date!!.after(Date(System.currentTimeMillis()))) {
                cellFilter.add(cells[i])
            }
        }

        //baseFragment.setEventsIntoRecyclerView(cellFilter)
        return cellFilter
    }

}