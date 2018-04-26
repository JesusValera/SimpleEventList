package com.rad4m.jesusreales.simpleeventlist.events.fragment

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rad4m.jesusreales.simpleeventlist.R
import com.rad4m.jesusreales.simpleeventlist.base.BaseFragment
import com.rad4m.jesusreales.simpleeventlist.data.model.CellElement

class PastEventsFragment : BaseFragment(), FragmentEventsContract.View {

    companion object {
        fun newInstance(): PastEventsFragment = PastEventsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_with_events, container, false)

        mRecyclerView = mView.findViewById(R.id.rvEvents)
        mRecyclerView.setHasFixedSize(true)

        mPresenter = PastEventsPresenter(this)
        mPresenter.filterEvents(mView)

        val swipeRefresh: SwipeRefreshLayout = mView.findViewById(R.id.swipe)
        swipeRefresh.setOnRefreshListener {
            mPresenter.filterEvents(mView)

            if (swipeRefresh.isRefreshing) {
                swipeRefresh.isRefreshing = false
            }
        }

        return mView
    }

    override fun setEventsIntoRecyclerView(cellElements: ArrayList<CellElement>) {
        recreateAdapter(mView, cellElements)
    }
}