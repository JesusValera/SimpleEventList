package com.rad4m.jesusreales.simpleeventlist.data

import android.arch.persistence.room.*
import com.rad4m.jesusreales.simpleeventlist.data.model.Event

@Dao
interface EventDao {

    @get:Query("SELECT * FROM event")
    val all: ArrayList<Event>

    @Query("SELECT * FROM event WHERE name = :name")
    fun findByName(name: String)

    @Insert()
    fun insert(event: Event)

    @Delete
    fun delete(event: Event)

    @Update
    fun update(event: Event)
}