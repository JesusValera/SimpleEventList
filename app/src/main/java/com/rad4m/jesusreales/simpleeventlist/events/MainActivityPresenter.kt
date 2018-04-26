package com.rad4m.jesusreales.simpleeventlist.events

import android.content.Context
import com.rad4m.jesusreales.simpleeventlist.data.model.Event

class MainActivityPresenter(private val mView: MainActivityContract.View): MainActivityContract.Presenter {

    private var mModel: MainActivityContract.Model = MainActivityModel()

    override fun start() { }

    override fun deleteEvent(context: Context, event: Event) {
        mModel.deleteEvent(context, event)
        mView.start()
    }

}