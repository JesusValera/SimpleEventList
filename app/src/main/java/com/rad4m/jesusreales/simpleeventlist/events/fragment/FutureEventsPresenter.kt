package com.rad4m.jesusreales.simpleeventlist.events.fragment

import android.text.format.DateUtils
import android.view.View
import com.rad4m.jesusreales.simpleeventlist.data.model.CellElement
import com.rad4m.jesusreales.simpleeventlist.data.model.Event
import com.rad4m.jesusreales.simpleeventlist.events.MainActivity
import java.util.*

class FutureEventsPresenter(val baseFragment: FragmentEventsContract.View) : FragmentEventsContract.Presenter {

    private val mModel: FragmentEventModel = FragmentEventModel()

    init {
        baseFragment.mPresenter = this
    }

    override fun filterEvents(view: View) {
        val events = mModel.getEvents(view.context)
        val cells = eventToCellEvent(events)
        val cellFilter = arrayListOf<CellElement>()
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

        baseFragment.setEventsIntoRecyclerView(cellFilter)
    }

    private fun eventToCellEvent(events: List<Event>) : ArrayList<CellElement> {
        val cellFilter = arrayListOf<CellElement>()
        for (i in events) {
            val cell = CellElement(i)
            cellFilter.add(cell)
        }

        return cellFilter
    }

}