package com.rad4m.jesusreales.simpleeventlist.events.fragment

import com.rad4m.jesusreales.simpleeventlist.BaseView
import com.rad4m.jesusreales.simpleeventlist.data.model.CellElement
import com.rad4m.jesusreales.simpleeventlist.events.MainActivity

interface FragmentEventsContract {

    interface View : BaseView<Presenter> {

        fun setEventsIntoRecyclerView(cellElements : ArrayList<CellElement>)
    }

    interface Presenter {

        fun filterEvents(activity : MainActivity) : ArrayList<CellElement> // Should not return the list. SQLite.
                            // Do not pass any activity....!

        //fun filterEvents() // The good one.

    }
}