package com.example.memorygame.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memorygame.util.ThemeManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val themeManager: ThemeManager
) : ViewModel() {
    val themeMode = themeManager.themeMode
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "light")

    fun setThemeMode(mode: String) {
        viewModelScope.launch {
            themeManager.setThemeMode(mode)
        }
    }
}