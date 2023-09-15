package com.ilyakoz.decoratemate.presentation.settings

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.ilyakoz.decoratemate.R
import com.ilyakoz.decoratemate.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {



    private val binding by lazy {
        ActivitySettingsBinding.inflate(layoutInflater)
    }


    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)

        val selectedTheme = sharedPrefs.getString(getString(R.string.key_theme), "")

        if (selectedTheme.isNullOrEmpty()) {
            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_NO -> {
                    // Светлая тема
                    binding.themedSwitch.isChecked = false
                }
                Configuration.UI_MODE_NIGHT_YES -> {
                    // Темная тема
                    binding.themedSwitch.isChecked = true
                }
            }
        } else {
            binding.themedSwitch.isChecked = selectedTheme == "dark"
        }

        binding.themedSwitch.setOnCheckedChangeListener { _, isChecked ->
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
