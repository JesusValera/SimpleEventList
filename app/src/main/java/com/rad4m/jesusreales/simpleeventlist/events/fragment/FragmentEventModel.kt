package com.rad4m.jesusreales.simpleeventlist.events.fragment

import android.content.Context
import com.rad4m.jesusreales.simpleeventlist.data.AppDatabase
import com.rad4m.jesusreales.simpleeventlist.data.model.Event

class FragmentEventModel : FragmentEventsContract.Model {

    override fun getEvents(context: Context): List<Event> {
        return AppDatabase.getDatabase(context).eventDao.all()
    }

}