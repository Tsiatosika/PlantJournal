package com.example.bloom.service

import android.net.Uri
import kotlinx.coroutines.delay

class AIService {

    suspend fun analyzePlantImage(imageUri: Uri): AnalysisResult {
        // Simulation d'un appel API - À remplacer par une vraie API
        delay(2000) // Simule le temps de traitement
        return simulateAIAnalysis()
    }

    private fun simulateAIAnalysis(): AnalysisResult {
        val plantNames = listOf(
            "Fougère des bois", "Tournesol Géant", "Lavande Vraie",
            "Menthe Poivrée", "Rose de Damas", "Fougère Aigle",
            "Chêne Sessile", "Tourneau Géant"
        )

        val plantFacts = listOf(
            "Cette fougère commune pousse dans les forêts tempérées. Ses spores se trouvent sous les feuilles et permettent sa reproduction.",
            "Le tournesol suit la trajectoire du soleil durant la journée. Chaque fleur est en réalité composée de milliers de petites fleurs.",
            "La lavande est utilisée depuis l'antiquité pour ses propriétés calmantes et son parfum envoûtant dans les jardins méditerranéens.",
            "La menthe poivrée est réputée pour ses vertus digestives. Elle s'étend rapidement grâce à ses rhizomes souterrains.",
            "La rose de Damas est célèbre pour son parfum utilisé en parfumerie. Elle fleurit abondamment au début de l'été.",
            "La fougère aigle forme de vastes tapis dans les sous-bois. Ses crosses sont comestibles au printemps."
        )

        return AnalysisResult(
            name = plantNames.random(),
            summary = plantFacts.random()
        )
    }
}

data class AnalysisResult(
    val name: String,
    val summary: String
)