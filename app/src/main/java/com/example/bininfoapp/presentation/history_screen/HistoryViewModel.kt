package com.example.bininfoapp.presentation.history_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bininfoapp.domain.use_case.GetHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistoryUseCase: GetHistoryUseCase
) : ViewModel() {

    private val _state = mutableStateOf(HistoryState())
    val state: State<HistoryState> = _state

    init {
        getHistory()
    }

    private fun getHistory() {
        // Вызываем наш use case, который возвращает Flow
        getHistoryUseCase()
            .onEach { history ->
                // Каждый раз, когда данные в БД меняются,
                // Flow присылает нам новый список.
                // Мы просто обновляем состояние.
                _state.value = state.value.copy(
                    historyItems = history
                )
            }.launchIn(viewModelScope)
    }
}