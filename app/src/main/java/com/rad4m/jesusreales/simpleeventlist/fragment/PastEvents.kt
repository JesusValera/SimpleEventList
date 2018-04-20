package com.rad4m.jesusreales.simpleeventlist.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
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
import kotlin.collections.ArrayList

class PastEvents : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: EventAdapter

    companion object {
        fun newInstance(): PastEvents = PastEvents()
    }

    private fun selectPastEvents(): ArrayList<CellElement> {
        val activity = activity as MainActivity
        val cells = activity.cellElements
        val cellFilter = arrayListOf<CellElement>()
        cells.sortDescending()

        for (i in cells.indices) {
            /*if (DateUtils.isToday(cells[i].event!!.date.time)) {
                cellFilter.add(cells[i])
                continue
            }

            if (cells[i].event!!.date.before(Date(System.currentTimeMillis()))) {
                cellFilter.add(cells[i])
            }*/
            val date = returnCompleteDate(cells[i].event!!.date,
                    cells[i].event!!.startTime.subSequence(0, 2).toString().toInt(),
                    cells[i].event!!.startTime.subSequence(3, 5).toString().toInt())
            if (date.timeInMillis < System.currentTimeMillis()) {
                cellFilter.add(cells[i])
            }
        }

        return cellFilter
    }

    private fun returnCompleteDate(date: Date, hours: Int, minutes: Int) : Calendar {
        val cal: Calendar = GregorianCalendar()
        cal.time = date
        cal.add(Calendar.HOUR, hours)
        cal.add(Calendar.MINUTE, minutes)

        return cal
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_with_events, container, false)

        recyclerView = view.findViewById(R.id.rvEvents)
        recyclerView.setHasFixedSize(true)

        adapter = EventAdapter(view.context, selectPastEvents())

        adapter.setOnLongClickListener(View.OnLongClickListener {
            val position = recyclerView.getChildAdapterPosition(it)
            val element = adapter.getEventByPos(position)
            val eventOption = EventOptions()
            eventOption.setCellElement(element)
            eventOption.show(activity?.fragmentManager, "tag")

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

        return view
    }

    override fun onResume() {
        recreateAdapter()
        super.onResume()
    }

    fun recreateAdapter() {
        val activity = activity as MainActivity
        adapter = EventAdapter(context!!, selectPastEvents())
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