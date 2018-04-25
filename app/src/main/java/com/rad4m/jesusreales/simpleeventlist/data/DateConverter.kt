package com.rad4m.jesusreales.simpleeventlist.data

import android.arch.persistence.room.TypeConverter
import java.util.*

object DateConverter {

    @TypeConverter
    fun toDate(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return (if (date == null) null else date.time)
    }
}