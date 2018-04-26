package com.rad4m.jesusreales.simpleeventlist.events

import android.content.Context
import com.rad4m.jesusreales.simpleeventlist.data.AppDatabase
import com.rad4m.jesusreales.simpleeventlist.data.model.Event

class MainActivityModel : MainActivityContract.Model {

    override fun deleteEvent(context: Context, event: Event) {
        AppDatabase.getDatabase(context).eventDao.delete(event)
    }

}