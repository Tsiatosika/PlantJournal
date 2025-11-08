package com.example.bloom.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plant_discoveries")
data class PlantDiscoveryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: String,
    val name: String,
    val summary: String,
    val imagePath: String,
    val timestamp: Long = System.currentTimeMillis()
)