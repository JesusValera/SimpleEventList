package com.rad4m.jesusreales.simpleeventlist.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.rad4m.jesusreales.simpleeventlist.R
import com.rad4m.jesusreales.simpleeventlist.model.Event
import java.text.SimpleDateFormat
import java.util.*
import android.content.Intent
import android.net.Uri
import com.rad4m.jesusreales.simpleeventlist.model.CellElement
import java.text.ParseException

class EventAdapter(var context: Context, cellElements: ArrayList<CellElement>) : RecyclerView.Adapter<EventAdapter.EventsViewHolder>(), View.OnLongClickListener {

    private lateinit var cellElements: List<CellElement>

    private fun formatDate(event: Event): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(event.date)
    }

    private fun isToday(dateString: String) : String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val today = sdf.format(Date(System.currentTimeMillis()))
        val yesterday = sdf.format(Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000))
        val tomorrow = sdf.format(Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))

        return when(dateString) {
            today -> "Today"
            yesterday -> "Yesterday"
            tomorrow -> "Tomorrow"
            else -> dateString
        }
    }

    private fun createDividers(cellElements: ArrayList<CellElement>) {
        //cellElements.sort()
        val cellEventsAndDividers: ArrayList<CellElement> = arrayListOf()

        for (i in cellElements.indices) {

            if (cellEventsAndDividers.size == 0) {
                val date = isToday(formatDate(cellElements[i].event!!))
                val divider = CellElement(date)

                cellEventsAndDividers.add(divider)
            }

            if (cellEventsAndDividers[cellEventsAndDividers.size - 1].type == CellElement.TYPE.Event)
                if (cellEventsAndDividers[cellEventsAndDividers.size - 1].event!!.date != cellElements[i].event!!.date) {
                    val dateEvent = isToday(formatDate(cellElements[i].event!!))
                    val divider = CellElement(dateEvent)

                    cellEventsAndDividers.add(divider)
                }

            cellEventsAndDividers.add(cellElements[i])
        }

        this.cellElements = cellEventsAndDividers
    }

    private lateinit var listener: View.OnLongClickListener

    override fun getItemViewType(position: Int): Int {
        if (cellElements[position].type == CellElement.TYPE.Event) {
            return 1
        } else {
            return 2
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        var itemView: View? = null
        when (viewType) {
            1 -> {
                itemView = LayoutInflater.from(parent.context).inflate(R.layout.cardview_event, parent, false)
                itemView.setOnLongClickListener(this)
            }
            2 -> {
                itemView = LayoutInflater.from(parent.context).inflate(R.layout.cardview_date, parent, false)
            }
        }

        return EventsViewHolder(context, itemView!!)
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        val event: CellElement = cellElements[position]


        holder.bindEvent(event)
    }

    override fun getItemCount(): Int {
        return cellElements.size
    }

    fun getEventByPos(position: Int): Event? {
        return cellElements[position].event
    }

    override fun onLongClick(v: View?): Boolean {
        return listener.onLongClick(v)
    }

    fun setOnLongClickListener(listener: View.OnLongClickListener) {
        this.listener = listener
    }

    class EventsViewHolder constructor(private var context: Context, itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindEvent(cellElement: CellElement) {

            if (cellElement.type == CellElement.TYPE.Event) {
                val ivPicture: ImageView? = itemView.findViewById(R.id.ivPicture)
                val tvLocation: TextView = itemView.findViewById(R.id.tvLocation)
                val tvName: TextView = itemView.findViewById(R.id.tvName)
                val tvDate: TextView = itemView.findViewById(R.id.tvDate)
                val tvTime: TextView = itemView.findViewById(R.id.tvTime)

                // Event.
                val event = cellElement.event
                tvName.text = event?.name
                tvLocation.text = event?.location
                ivPicture?.setImageDrawable(event?.picture)
                tvTime.text = event?.time

                try {
                    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    tvDate.text = sdf.format(event?.date)
                } catch (e: ParseException) {

                }

                tvLocation.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse("http://maps.google.com/maps?q=${event?.location}")

                    if (intent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(intent)
                    }
                }
            }

            if (cellElement.type == CellElement.TYPE.Divider) {
                val tvDate: TextView = itemView.findViewById(R.id.tvDate)
                tvDate.text = cellElement.text
            }

        }

    }

    init {
        createDividers(cellElements)
    }

}