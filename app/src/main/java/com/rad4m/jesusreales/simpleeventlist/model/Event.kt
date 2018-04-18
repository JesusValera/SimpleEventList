package com.rad4m.jesusreales.simpleeventlist.model

import android.graphics.drawable.Drawable
import java.util.*

// https://code.tutsplus.com/series/kotlin-from-scratch--cms-1209
// https://code.tutsplus.com/tutorials/kotlin-from-scratch-advanced-functions--cms-29534 -- Higher-Order Functions
// https://try.kotlinlang.org/#/Kotlin%20Koans/Introduction/Nullable%20types/Task.kt

class Event(var name: String) {

    var location: String = ""
    var picture: Drawable? = null
    var date: Date = Date()
    var time: String = ""

    fun addEvent(events: ArrayList<Event>) {
        // Check ID or some to avoid repeated events?
        events += this
    }

    fun removeEvent(events: ArrayList<Event>) {
        // TODO I think that it doesnt work... yet. (comparator?)
        if (events.contains(this)) {
            events.remove(this)
        }
    }

}