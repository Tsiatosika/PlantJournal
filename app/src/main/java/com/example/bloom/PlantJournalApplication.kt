package com.example.bloom

import android.app.Application

class PlantJournalApplication : Application() {
    val database: com.example.bloom.data.database.PlantDatabase by lazy {
        com.example.bloom.data.database.PlantDatabase.getDatabase(this)
    }
}