package com.rad4m.jesusreales.simpleeventlist.ui.events.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rad4m.jesusreales.simpleeventlist.R
import com.rad4m.jesusreales.simpleeventlist.ui.BaseFragment
import com.rad4m.jesusreales.simpleeventlist.data.model.CellElement

class FutureEventsFragment : BaseFragment(), FragmentEventsContract.View {

    companion object {
        fun newInstance(): FutureEventsFragment = FutureEventsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_with_events, container, false)

        mRecyclerView = mView.findViewById(R.id.rvEvents)
        mRecyclerView.setHasFixedSize(true)

        mPresenter = FutureEventsPresenter(this)

        return mView
    }

    override fun setEventsIntoRecyclerView(cellElements: ArrayList<CellElement>) {
        recreateAdapter(mView, cellElements)
    }

    override fun start() { }
}