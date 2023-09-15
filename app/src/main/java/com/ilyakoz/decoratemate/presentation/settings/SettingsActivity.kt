package com.ilyakoz.decoratemate.presentation.settings

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import com.ilyakoz.decoratemate.R

class SettingsActivity : AppCompatActivity() {

    private lateinit var themedSwitch: SwitchCompat
    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        themedSwitch = findViewById(R.id.themedSwitch)

        val selectedTheme = sharedPrefs.getString(getString(R.string.key_theme), "")

        if (selectedTheme.isNullOrEmpty()) {
            val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            when (currentNightMode) {
                Configuration.UI_MODE_NIGHT_NO -> {
                    // Светлая тема
                    themedSwitch.isChecked = false
                }
                Configuration.UI_MODE_NIGHT_YES -> {
                    // Темная тема
                    themedSwitch.isChecked = true
                }
            }
        } else {
            themedSwitch.isChecked = selectedTheme == "dark"
        }

        themedSwitch.setOnCheckedChangeListener { _, isChecked ->
            val newTheme = if (isChecked) "dark" else "light"
            sharedPrefs.edit().putString(getString(R.string.key_theme), newTheme).apply()

            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            recreate()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, SettingsActivity::class.java)
        }
    }
}

