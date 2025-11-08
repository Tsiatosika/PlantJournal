package com.example.bloom.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bloom.data.database.entities.PlantDiscoveryEntity

@Database(
    entities = [PlantDiscoveryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PlantDatabase : RoomDatabase() {
    abstract fun plantDao(): PlantDao

    companion object {
        @Volatile
        private var Instance: PlantDatabase? = null

        fun getDatabase(context: Context): PlantDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    PlantDatabase::class.java,
                    "plant_database"
                ).build().also { Instance = it }
            }
        }
    }
}