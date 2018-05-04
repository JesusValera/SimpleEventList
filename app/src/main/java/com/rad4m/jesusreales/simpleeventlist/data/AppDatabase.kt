package com.rad4m.jesusreales.simpleeventlist.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.rad4m.jesusreales.simpleeventlist.data.model.Event

@Database(entities = [Event::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val eventDao: EventDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Gets the singleton instance of WordDatabase.
         *
         * @param context The context.
         * @return The singleton instance of WordDatabase.
         */
        fun getDatabase(context: Context): AppDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "events.db")
                        .allowMainThreadQueries()
                        .build()
    }
}