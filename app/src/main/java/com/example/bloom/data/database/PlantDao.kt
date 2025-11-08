package com.example.bloom.data.database

import androidx.room.*
import com.example.bloom.data.database.entities.PlantDiscoveryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantDao {
    @Query("SELECT * FROM plant_discoveries WHERE userId = :userId ORDER BY timestamp DESC")
    fun getAllByUser(userId: String): Flow<List<PlantDiscoveryEntity>>

    @Insert
    suspend fun insert(discovery: PlantDiscoveryEntity)

    @Delete
    suspend fun delete(discovery: PlantDiscoveryEntity)

    @Query("SELECT * FROM plant_discoveries WHERE id = :id")
    suspend fun getById(id: Long): PlantDiscoveryEntity?
}