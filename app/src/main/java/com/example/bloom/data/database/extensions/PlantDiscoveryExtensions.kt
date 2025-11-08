package com.example.bloom.data.database.extensions

import com.example.bloom.data.database.entities.PlantDiscoveryEntity
import com.example.bloom.data.model.PlantDiscovery

fun PlantDiscoveryEntity.toPlantDiscovery(): PlantDiscovery {
    return PlantDiscovery(
        id = id,
        userId = userId,
        name = name,
        summary = summary,
        imagePath = imagePath,
        timestamp = timestamp
    )
}

fun PlantDiscovery.toPlantDiscoveryEntity(): PlantDiscoveryEntity {
    return PlantDiscoveryEntity(
        id = id,
        userId = userId,
        name = name,
        summary = summary,
        imagePath = imagePath,
        timestamp = timestamp
    )
}