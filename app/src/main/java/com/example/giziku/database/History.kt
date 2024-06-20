package com.example.giziku.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class History(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val imageUri: String,
    val foodName: String,
    val calories: Double,
    val fat: Double,
    val carbohydrates: Double,
    val confidenceScore: Float,
    val protein: Double,
    val result:String,
    val classificationResult: String
)