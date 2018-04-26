package com.rad4m.jesusreales.simpleeventlist.createEvents

import android.content.Context
import com.rad4m.jesusreales.simpleeventlist.data.AppDatabase
import com.rad4m.jesusreales.simpleeventlist.data.model.Event

class CreateEventModel : CreateEventContract.Model {

    override fun updateOrInsertEvent(context: Context, event: Event) {
        AppDatabase.getDatabase(context).eventDao.insert(event)
    }

}