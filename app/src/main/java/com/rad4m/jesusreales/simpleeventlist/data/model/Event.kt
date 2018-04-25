package com.rad4m.jesusreales.simpleeventlist.data.model

import android.arch.persistence.room.*
import org.jetbrains.annotations.NotNull
import java.util.*

@Entity
class Event {

    @PrimaryKey
    @NotNull
    var uid: String = UUID.randomUUID().toString()

    @ColumnInfo(name = "name")
    var name: String = ""

    @ColumnInfo(name = "location")
    var location: String = ""

    @ColumnInfo(name = "picture")
    var picture: Int = 0

    @ColumnInfo(name = "date")
    var date: Date? = null

    @ColumnInfo(name = "start_time")
    var startTime: String? = null

    @ColumnInfo(name = "end_time")
    var endTime: String? = null

}