package com.rad4m.jesusreales.simpleeventlist.data

import android.arch.persistence.room.*
import com.rad4m.jesusreales.simpleeventlist.data.model.Event

@Dao
interface EventDao {

    @Query("SELECT * FROM event")
    fun all(): List<Event>

    @Query("SELECT * FROM event WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): Event

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(event: Event)

    @Delete
    fun delete(event: Event)

    @Update
    fun update(event: Event)
}