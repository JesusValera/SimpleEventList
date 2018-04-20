package com.rad4m.jesusreales.simpleeventlist.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.rad4m.jesusreales.simpleeventlist.MainActivity
import com.rad4m.jesusreales.simpleeventlist.R
import com.rad4m.jesusreales.simpleeventlist.adapter.EventAdapter
import com.rad4m.jesusreales.simpleeventlist.dialog.EventOptions
import com.rad4m.jesusreales.simpleeventlist.model.CellElement
import java.util.*

class FutureEvents : Fragment() {

    lateinit var adapter: EventAdapter
    private lateinit var recyclerView: RecyclerView

    companion object {
        fun newInstance(): FutureEvents = FutureEvents()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_with_events, container, false)

        recyclerView = view.findViewById(R.id.rvEvents)
        recyclerView.setHasFixedSize(true)

        adapter = EventAdapter(view.context, selectFutureEvents())

        adapter.setOnLongClickListener(View.OnLongClickListener {
            val position = recyclerView.getChildAdapterPosition(it)
            val element = adapter.getEventByPos(position)
            val eventOption = EventOptions()
            eventOption.setCellElement(element)
            val activity = activity as MainActivity
            eventOption.show(activity.fragmentManager, "tag")

            return@OnLongClickListener true
        })

        if (adapter.itemCount != 0) {
            view.findViewById<ImageView>(R.id.ivRecyclerViewEmpty).visibility = View.INVISIBLE
            view.findViewById<TextView>(R.id.tvRecyclerViewEmpty).visibility = View.INVISIBLE
        } else {
            view?.findViewById<ImageView>(R.id.ivRecyclerViewEmpty)?.visibility = View.VISIBLE
            view?.findViewById<TextView>(R.id.tvRecyclerViewEmpty)?.visibility = View.VISIBLE
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val swipeRefresh: SwipeRefreshLayout = view.findViewById(R.id.swipe)
        swipeRefresh.setOnRefreshListener {
            recreateAdapter()

            if (swipeRefresh.isRefreshing) {
                swipeRefresh.isRefreshing = false
            }
        }

        return view
    }

    private fun selectFutureEvents() : ArrayList<CellElement> {
        val cellFilter = arrayListOf<CellElement>()
        val activity = activity as MainActivity
        val cells = activity.cellElements
        cells.sort()

        for (i in cells.indices) {
            if (DateUtils.isToday(cells[i].event!!.date.time)) {
                cellFilter.add(cells[i])
                continue
            }
            if (cells[i].event!!.date.after(Date(System.currentTimeMillis()))) {
                cellFilter.add(cells[i])
            }
        }

        return cellFilter
    }

    fun recreateAdapter() {
        val activity = activity as MainActivity
        adapter = EventAdapter(context!!, selectFutureEvents())
        adapter.setOnLongClickListener(View.OnLongClickListener {
            val position = recyclerView.getChildAdapterPosition(it)
            val element = adapter.getEventByPos(position)
            val eventOption = EventOptions()
            eventOption.setCellElement(element)
            eventOption.show(activity.fragmentManager, "tag")

            return@OnLongClickListener true
        })

        if (adapter.itemCount != 0) {
            view?.findViewById<ImageView>(R.id.ivRecyclerViewEmpty)?.visibility = View.INVISIBLE
            view?.findViewById<TextView>(R.id.tvRecyclerViewEmpty)?.visibility = View.INVISIBLE
        } else {
            view?.findViewById<ImageView>(R.id.ivRecyclerViewEmpty)?.visibility = View.VISIBLE
            view?.findViewById<TextView>(R.id.tvRecyclerViewEmpty)?.visibility = View.VISIBLE
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

}