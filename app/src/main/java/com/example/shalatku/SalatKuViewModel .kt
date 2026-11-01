package com.example.shalatku

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SalatKuViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SalatKuUiState())
    val uiState: StateFlow<SalatKuUiState> = _uiState.asStateFlow()

    // Penghitung panggilan: bukan UI state, karena tidak ada screen yang membacanya
    private var loadCount = 0

    // --- Prayer times (HW13) ---
    fun loadPrayerTimes() {
        // Kalau sedang Loading, panggilan baru tidak melakukan apa-apa
        if (_uiState.value.prayerTimes is PrayerTimesUiState.Loading) return

        _uiState.update { it.copy(prayerTimes = PrayerTimesUiState.Loading) }

        loadCount++
        val shouldFail = loadCount % 2 == 0   // panggilan ke-2, 4, 6, ... gagal

        viewModelScope.launch {
            delay(1500)
            _uiState.update {
                it.copy(
                    prayerTimes = if (shouldFail) {
                        PrayerTimesUiState.Error("Gagal memuat jadwal shalat. Coba lagi.")
                    } else {
                        PrayerTimesUiState.Success(samplePrayers)
                    }
                )
            }
        }
    }

    // --- Tracker ---
    fun togglePrayer(name: String) {
        _uiState.update { state ->
            val done = if (name in state.donePrayers) {
                state.donePrayers - name
            } else {
                state.donePrayers + name
            }
            state.copy(donePrayers = done)
        }
    }

    fun clearPrayers() {
        _uiState.update { it.copy(donePrayers = emptyList()) }
    }

    fun selectDay(index: Int) {
        _uiState.update { it.copy(selectedDay = index) }
    }

    // --- Tasbih ---
    fun incrementTasbih() {
        _uiState.update { state ->
            val next = state.tasbihCount + 1
            if (next >= state.roundSize) {
                state.copy(tasbihCount = 0, completedRounds = state.completedRounds + 1)
            } else {
                state.copy(tasbihCount = next)
            }
        }
    }

    fun resetTasbih() {
        _uiState.update { it.copy(tasbihCount = 0, completedRounds = 0) }
    }

    fun setRoundSize(size: Int) {
        _uiState.update { it.copy(roundSize = size, tasbihCount = 0) }
    }
}