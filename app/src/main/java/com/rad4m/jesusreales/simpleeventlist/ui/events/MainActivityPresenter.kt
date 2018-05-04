package com.rad4m.jesusreales.simpleeventlist.ui.events

class MainActivityPresenter(private val mView: MainActivityContract.View): MainActivityContract.Presenter {

    override fun start() { }

    override fun activityCreateEvent() {
        mView.startActivityCreateEvent()
    }
}