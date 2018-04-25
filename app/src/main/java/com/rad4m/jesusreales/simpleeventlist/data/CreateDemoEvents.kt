package com.rad4m.jesusreales.simpleeventlist.data

import android.content.Context
import com.rad4m.jesusreales.simpleeventlist.R
import com.rad4m.jesusreales.simpleeventlist.data.model.CellElement
import com.rad4m.jesusreales.simpleeventlist.data.model.Event
import java.util.*

class CreateDemoEvents(val context: Context) {

    private val pictures = arrayListOf(R.drawable.comp0, R.drawable.comp1, R.drawable.comp2, R.drawable.comp3)

    fun createDemoEvents(cellElements : ArrayList<CellElement>) {
        val dates = createDates()
        for (i in 1..5) {
            val event = Event("Event n${i}")
            event.picture = getRandomPicture()
            event.date = dates[i - 1]
            event.startTime = "10:00"
            event.endTime = "13:30"
            event.location = "Kazimierza Kordylewskiego 7-5, Kraków"
            val cellElement = CellElement(event)
            cellElements.add(cellElement)
        }
    }

    private fun createDates(): List<Date> {
        val date = Date()
        val date2 = Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000)
        val date3 = Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)
        val date4 = Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000 * 2)

        return listOf(date, date2, date2, date3, date4)
    }

    private fun random(n: Int) = (Math.random() * n).toInt()

    fun getRandomPicture() : Int {
        return pictures[random(4)]
    }
}