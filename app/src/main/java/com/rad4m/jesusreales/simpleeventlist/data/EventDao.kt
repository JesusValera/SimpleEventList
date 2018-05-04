package com.rad4m.jesusreales.simpleeventlist.data

import android.arch.persistence.room.*
import com.rad4m.jesusreales.simpleeventlist.data.model.Event
import io.reactivex.Flowable

@Dao
interface EventDao {

    @Query("SELECT * FROM event")
    fun all(): Flowable<List<Event>>

    @Query("SELECT * FROM event WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): Flowable<Event>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(event: Event)

    @Delete
    fun delete(event: Event)

}