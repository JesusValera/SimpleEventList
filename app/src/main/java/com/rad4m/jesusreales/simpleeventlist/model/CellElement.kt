package com.rad4m.jesusreales.simpleeventlist.model

class CellElement : Comparable<CellElement> {

    enum class TYPE {
        Event, Divider
    }

    var event: Event? = null
    var type: TYPE = TYPE.Event
    var text: String = ""

    constructor(event: Event) {
        this.type = TYPE.Event
        this.event = event
    }

    constructor(text: String) {
        this.type = TYPE.Divider
        this.text = text
    }

    override fun compareTo(other: CellElement): Int {
        return if (this.event!!.date.after(other.event!!.date)) {
            1
        } else {
            -1
        }
    }
}