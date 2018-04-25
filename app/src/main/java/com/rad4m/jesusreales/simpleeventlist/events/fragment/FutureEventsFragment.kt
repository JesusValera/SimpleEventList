package com.rad4m.jesusreales.simpleeventlist.events.fragment

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rad4m.jesusreales.simpleeventlist.R
import com.rad4m.jesusreales.simpleeventlist.data.model.CellElement
import kotlin.collections.ArrayList

class FutureEventsFragment : BaseFragment(), FragmentEventsContract.View {

    override lateinit var mPresenter: FragmentEventsContract.Presenter

    companion object {
        fun newInstance(): FutureEventsFragment = FutureEventsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.fragment_with_events, container, false)

        mRecyclerView = mView.findViewById(R.id.rvEvents)
        mRecyclerView.setHasFixedSize(true)

        mPresenter = FutureEventsPresenter(this)

        recreateAdapter(mView)

        val swipeRefresh: SwipeRefreshLayout = mView.findViewById(R.id.swipe)
        swipeRefresh.setOnRefreshListener {
            recreateAdapter(mView)

            if (swipeRefresh.isRefreshing) {
                swipeRefresh.isRefreshing = false
            }
        }

        return mView
    }

    override fun setEventsIntoRecyclerView(cellElements: ArrayList<CellElement>) {

    }

}