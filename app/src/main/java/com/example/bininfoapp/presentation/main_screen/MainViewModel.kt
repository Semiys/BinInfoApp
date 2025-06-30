package com.example.bininfoapp.presentation.main_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bininfoapp.domain.use_case.GetBinInfoUseCase
import com.example.bininfoapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getBinInfoUseCase: GetBinInfoUseCase
) : ViewModel() {

    // Приватное, изменяемое состояние. Меняется только внутри ViewModel.
    private val _state = mutableStateOf(MainScreenState())
    // Публичное, не изменяемое состояние. Доступно для UI только для чтения.
    val state: State<MainScreenState> = _state

    private var searchJob: Job? = null

    /**
     * Вызывается каждый раз, когда пользователь вводит символ в поле.
     */
    fun onBinInput(input: String) {
        _state.value = _state.value.copy(binInput = input.filter { it.isDigit() })
    }

    /**
     * Запускает поиск информации по BIN.
     */
    fun onSearch() {
        // Отменяем предыдущий запрос, если он еще не завершился.
        searchJob?.cancel()
        searchJob = getBinInfoUseCase(state.value.binInput)
            .onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = true,
                            binInfo = null,
                            error = null
                        )
                    }
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            binInfo = result.data,
                            error = null
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            binInfo = null,
                            error = result.message
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }
}