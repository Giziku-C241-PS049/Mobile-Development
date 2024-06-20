package com.example.giziku.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [History::class], version = 3)
abstract class HistoryRoomDatabase : RoomDatabase() {
    abstract fun HistoryDao(): HistoryDao

    companion object {
        @Volatile
        private var INSTANCE:HistoryRoomDatabase? = null

        fun getDatabase (context: Context): HistoryRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HistoryRoomDatabase::class.java,
                    "history_room_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}