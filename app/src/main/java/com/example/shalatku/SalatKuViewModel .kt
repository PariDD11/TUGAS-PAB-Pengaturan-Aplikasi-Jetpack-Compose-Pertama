package com.example.shalatku

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SalatKuViewModel : ViewModel() {

    // --- Tasbih ---
    var tasbihCount by mutableIntStateOf(0)
        private set

    var completedRounds by mutableIntStateOf(0)
        private set

    val roundSizeOptions = listOf(33, 99, 100)

    private var _roundSize by mutableIntStateOf(33)
    val roundSize: Int get() = _roundSize

    // --- Tracker ---
    private val _donePrayers = mutableStateListOf<String>()
    val donePrayers: List<String> get() = _donePrayers

    // --- Home ---
    var selectedDay by mutableIntStateOf(0)
        private set

    // --- Fungsi tasbih ---
    fun incrementTasbih() {
        tasbihCount++
        if (tasbihCount >= roundSize) {
            completedRounds++
            tasbihCount = 0
        }
    }

    fun resetTasbih() {
        tasbihCount = 0
        completedRounds = 0
    }

    fun setRoundSize(size: Int) {
        _roundSize = size
        tasbihCount = 0          // count di-reset, rounds tetap
    }

    // --- Fungsi tracker ---
    fun togglePrayer(name: String) {
        if (name in _donePrayers) _donePrayers.remove(name) else _donePrayers.add(name)
    }

    fun clearPrayers() {
        _donePrayers.clear()
    }

    // --- Fungsi home ---
    fun selectDay(index: Int) {
        selectedDay = index
    }
}