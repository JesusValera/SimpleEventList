package com.rad4m.jesusreales.simpleeventlist.data.model

import android.arch.persistence.room.*
import com.rad4m.jesusreales.simpleeventlist.data.Converters
import org.jetbrains.annotations.NotNull
import java.util.*

// https://code.tutsplus.com/series/kotlin-from-scratch--cms-1209
// https://code.tutsplus.com/tutorials/kotlin-from-scratch-advanced-functions--cms-29534 -- Higher-Order Functions
// https://try.kotlinlang.org/#/Kotlin%20Koans/Introduction/Nullable%20types/Task.kt

@Entity(tableName = "event")
@TypeConverters(Converters::class)
class Event(@NotNull @PrimaryKey @ColumnInfo(name = "name") var name: String) {

    @ColumnInfo(name = "location")
    var location: String = ""

    @ColumnInfo(name = "picture")
    var picture: Int = 0

    @ColumnInfo(name = "date")
    var date: Date = Date()

    @ColumnInfo(name = "start_time")
    var startTime: String = ""

    @ColumnInfo(name = "end_time")
    var endTime: String = ""

}