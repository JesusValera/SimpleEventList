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
    //abstract fun eventDao(): EventDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        /**
         * Gets the singleton instance of WordDatabase.
         *
         * @param context The context.
         * @return The singleton instance of WordDatabase.
         */
        @Synchronized
        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    if (INSTANCE == null)
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                                AppDatabase::class.java, "events.db")
                                .allowMainThreadQueries()
                                .build()
                }

            }
            return INSTANCE!!
        }
    }

}