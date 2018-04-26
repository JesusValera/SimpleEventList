package com.rad4m.jesusreales.simpleeventlist.data.model

import android.arch.persistence.room.*
import com.rad4m.jesusreales.simpleeventlist.R
import org.jetbrains.annotations.NotNull
import java.util.*

@Entity(tableName = "event", indices = [(Index(value = ["name"], unique = true))])
class Event {

    companion object {
        val pictures = arrayListOf(R.drawable.comp0, R.drawable.comp1, R.drawable.comp2, R.drawable.comp3)
    }

    @PrimaryKey
    @NotNull
    var uid: String = UUID.randomUUID().toString()

    @ColumnInfo(name = "name")
    @NotNull
    var name: String = ""

    @ColumnInfo(name = "location")
    var location: String = ""

    @ColumnInfo(name = "date")
    var date: Date? = null

    @ColumnInfo(name = "start_time")
    var startTime: String? = null

    @ColumnInfo(name = "end_time")
    var endTime: String? = null

    @Ignore
    var picture: Int = getRandomPicture()

    @Ignore
    private fun getRandomPicture() = pictures[random(4)]

    @Ignore
    private fun random(n: Int) = (Math.random() * n).toInt()

}