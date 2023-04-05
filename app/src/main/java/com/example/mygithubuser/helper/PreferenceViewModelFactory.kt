package com.example.mygithubuser.helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mygithubuser.MainViewModel
import com.example.mygithubuser.SettingPreferences
import com.example.mygithubuser.ThemeSettingViewModel

class PreferenceViewModelFactory(private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(pref) as T
        }
        else if (modelClass.isAssignableFrom(ThemeSettingViewModel::class.java)) {
            return ThemeSettingViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}