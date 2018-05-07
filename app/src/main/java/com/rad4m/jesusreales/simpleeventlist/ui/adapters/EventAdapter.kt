package com.rad4m.jesusreales.simpleeventlist.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.rad4m.jesusreales.simpleeventlist.R
import com.rad4m.jesusreales.simpleeventlist.data.model.Event
import java.text.SimpleDateFormat
import java.util.*
import android.content.Intent
import android.net.Uri
import android.support.v4.content.res.ResourcesCompat
import com.rad4m.jesusreales.simpleeventlist.data.model.CellElement

class EventAdapter(var context: Context, cellElements: List<CellElement>) : RecyclerView.Adapter<EventAdapter.EventsViewHolder>(), View.OnLongClickListener {

    private lateinit var cellElements: List<CellElement>
    private lateinit var listener: View.OnLongClickListener

    init {
        createDividers(cellElements)
    }

    private fun createDividers(cellElements: List<CellElement>) {
        val cellEventsAndDividers: ArrayList<CellElement> = arrayListOf()

        for (i in cellElements.indices) {

            if (cellEventsAndDividers.isEmpty()) {
                val date = getDateString(formatDate(cellElements[i].event!!))
                val divider = CellElement(date)

                cellEventsAndDividers.add(divider)
            }

            if (cellEventsAndDividers[cellEventsAndDividers.size - 1].type == CellElement.TYPE.Event)
                if (!isSameDay( cellElements[i].event!!.date!!, cellEventsAndDividers[cellEventsAndDividers.size - 1].event!!.date!!)) {
                    val dateEvent = getDateString(formatDate(cellElements[i].event!!))
                    val divider = CellElement(dateEvent)

                    cellEventsAndDividers.add(divider)
                }

            cellEventsAndDividers.add(cellElements[i])
        }

        this.cellElements = cellEventsAndDividers
    }

    private fun isSameDay(date1: Date, date2: Date): Boolean {
        val fmt = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        return fmt.format(date1) == fmt.format(date2)
    }

    private fun getDateString(dateString: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val today = sdf.format(Date(System.currentTimeMillis()))
        val yesterday = sdf.format(Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000))
        val tomorrow = sdf.format(Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))

        return when (dateString) {
            today -> "Today"
            yesterday -> "Yesterday"
            tomorrow -> "Tomorrow"
            else -> {
                val d = sdf.parse(dateString)
                sdf.applyPattern("dd MMM yyyy")
                return sdf.format(d)
            }
        }
    }

    private fun formatDate(event: Event): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(event.date)
    }

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

    fun getEventByPos(position: Int): CellElement {
        return cellElements[position]
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
                val tvStartTime: TextView = itemView.findViewById(R.id.tvStartTime)
                val tvEndTime: TextView = itemView.findViewById(R.id.tvEndTime)

                val event = cellElement.event
                tvName.text = event?.name
                tvLocation.text = event?.location
                val drawable = ResourcesCompat.getDrawable(context.resources, event!!.picture, null)
                ivPicture?.setImageDrawable(drawable)
                tvStartTime.text = event.startTime
                tvEndTime.text = event.endTime

                tvLocation.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse("http://maps.google.com/maps?q=${event.location}")

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

}