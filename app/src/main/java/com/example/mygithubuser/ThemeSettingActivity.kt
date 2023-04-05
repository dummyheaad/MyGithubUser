package com.example.mygithubuser

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.mygithubuser.databinding.ActivityThemeSettingBinding
import com.example.mygithubuser.helper.PreferenceViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ThemeSettingActivity : AppCompatActivity() {

    private lateinit var activityThemeSettingBinding: ActivityThemeSettingBinding
    private lateinit var activityThemeSettingViewModel: ThemeSettingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityThemeSettingBinding = ActivityThemeSettingBinding.inflate(layoutInflater)
        setContentView(activityThemeSettingBinding.root)

        supportActionBar?.title = getString(R.string.setting)

        val pref = SettingPreferences.getInstance(dataStore)
        activityThemeSettingViewModel = ViewModelProvider(this, PreferenceViewModelFactory(pref))[ThemeSettingViewModel::class.java]
        activityThemeSettingViewModel.getThemeSetting().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                activityThemeSettingBinding.switchTheme.isChecked = true
            }
            else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                activityThemeSettingBinding.switchTheme.isChecked = false
            }
        }

        activityThemeSettingBinding.switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            activityThemeSettingViewModel.setThemeSetting(isChecked)
        }
    }
}