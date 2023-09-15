package com.ilyakoz.decoratemate.presentation.category

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ilyakoz.decoratemate.R
import com.ilyakoz.decoratemate.databinding.ActivityCategoryBinding
import com.ilyakoz.decoratemate.presentation.listPhoto.ListPhotoActivity

class CategoryActivity : AppCompatActivity() {



    private val binding by lazy {
        ActivityCategoryBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide() // Скрываем Action Bar






        binding.buttonSpace.setOnClickListener {
            startListPhotoActivity(getString(R.string.space))
        }
        binding.buttonNature.setOnClickListener {
            startListPhotoActivity(getString(R.string.nature))
        }
        binding.buttonNight.setOnClickListener {
            startListPhotoActivity(getString(R.string.night))
        }
        binding.buttonMorning.setOnClickListener {
            startListPhotoActivity(getString(R.string.morning))
        }
        binding.buttonSunset.setOnClickListener {
            startListPhotoActivity(getString(R.string.sunset))
        }
        binding.buttonWinter.setOnClickListener {
            startListPhotoActivity(getString(R.string.winter))
        }
        binding.buttonCity.setOnClickListener {
            startListPhotoActivity(getString(R.string.city))
        }
        binding.buttonForest.setOnClickListener {
            startListPhotoActivity(getString(R.string.forest))
        }
        binding.buttonRain.setOnClickListener {
            startListPhotoActivity(getString(R.string.rain))
        }
        binding.buttonSky.setOnClickListener {
            startListPhotoActivity(getString(R.string.sky))
        }

    }

    private fun startListPhotoActivity(category: String) {
        val intent = ListPhotoActivity.newIntent(this, category)
        startActivity(intent)
    }

}