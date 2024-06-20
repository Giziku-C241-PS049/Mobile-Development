package com.example.giziku.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HistoryDao {
    @Insert
    suspend fun insertPrediction(prediction: History)

    @Query("SELECT * FROM history")
    fun getAllHistory(): LiveData<List<History>>

    @Delete
    suspend fun deletePrediction(history: History)

}