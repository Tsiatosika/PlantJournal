package com.example.bloom.data.repository

import com.example.bloom.data.database.PlantDao
import com.example.bloom.data.database.extensions.toPlantDiscovery
import com.example.bloom.data.database.extensions.toPlantDiscoveryEntity
import com.example.bloom.data.model.PlantDiscovery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlantRepository(
    private val plantDao: PlantDao
) {
    fun getAllDiscoveriesByUser(userId: String): Flow<List<PlantDiscovery>> {
        return plantDao.getAllByUser(userId).map { entities ->
            entities.map { it.toPlantDiscovery() }
        }
    }

    suspend fun insertDiscovery(discovery: PlantDiscovery) {
        plantDao.insert(discovery.toPlantDiscoveryEntity())
    }

    suspend fun deleteDiscovery(discovery: PlantDiscovery) {
        plantDao.delete(discovery.toPlantDiscoveryEntity())
    }

    suspend fun getDiscoveryById(id: Long): PlantDiscovery? {
        return plantDao.getById(id)?.toPlantDiscovery()
    }

    fun getDiscoveryByIdFlow(discoveryId: Long): Flow<PlantDiscovery?> {
        return plantDao.getAllByUser("").map { discoveries ->
            discoveries.find { it.id == discoveryId }?.toPlantDiscovery()
        }
    }
}