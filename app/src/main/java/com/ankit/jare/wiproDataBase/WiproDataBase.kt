package com.ankit.jare.wiproDataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [WiproEntity::class], version = 1, exportSchema = false)
abstract class WiproDataBase : RoomDatabase() {

    abstract fun wiproDao(): WiproDao

    companion object {
        @Volatile
        private var INSTANCE: WiproDataBase? = null

        fun getDatabase(context: Context): WiproDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        WiproDataBase::class.java,
                        "wipro_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}