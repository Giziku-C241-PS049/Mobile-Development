package com.example.giziku.database

import androidx.lifecycle.LiveData

class HistoryRepository(private val historyDao: HistoryDao) {
    suspend fun insertPrediction(prediction: History) {
        historyDao.insertPrediction(prediction)
    }
    fun getAllHistory(): LiveData<List<History>> {
        return historyDao.getAllHistory()
    }
    suspend fun deleteHistory(history: History) {
        historyDao.deletePrediction(history)
    }
}