package com.rad4m.jesusreales.simpleeventlist.ui.events.fragment

import android.text.format.DateUtils
import android.view.View
import com.rad4m.jesusreales.simpleeventlist.data.model.CellElement
import com.rad4m.jesusreales.simpleeventlist.data.model.Event
import java.util.*
import kotlin.collections.ArrayList

class PastEventsPresenter(val baseFragment: FragmentEventsContract.View) : FragmentEventsContract.Presenter {

    init {
        baseFragment.mPresenter = this
    }

    override fun filterEvents(view: View, events: List<Event>) {
        val cells = eventToCellEvent(events)
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

        baseFragment.setEventsIntoRecyclerView(cellFilter)
    }

    private fun eventToCellEvent(events: List<Event>): ArrayList<CellElement> {
        val cellFilter = arrayListOf<CellElement>()
        for (i in events) {
            val cell = CellElement(i)
            cellFilter.add(cell)
        }

        return cellFilter
    }

}