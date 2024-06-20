package com.example.giziku.view.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giziku.database.History
import com.example.giziku.database.HistoryRepository
import kotlinx.coroutines.launch

class HistoryViewModel (private val historyRepository: HistoryRepository) : ViewModel() {

    val historyList: LiveData<List<History>> = historyRepository.getAllHistory()

    fun deleteHistory(history: History) {
        viewModelScope.launch {
            historyRepository.deleteHistory(history)
        }
    }
}
