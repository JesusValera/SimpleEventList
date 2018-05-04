package com.rad4m.jesusreales.simpleeventlist.ui.createEvents

class CreateEventPresenter(private val mView: CreateEventContract.View) : CreateEventContract.Presenter {

    override fun start() { }

    init {
        mView.initViews()
    }

}