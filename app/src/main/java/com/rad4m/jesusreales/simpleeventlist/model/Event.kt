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
    var startTime: String = ""
    var endTime: String = ""

}