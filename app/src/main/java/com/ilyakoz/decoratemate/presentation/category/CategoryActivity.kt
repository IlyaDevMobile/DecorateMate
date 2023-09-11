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


        binding.buttonSpace.setOnClickListener {
            val intent = ListPhotoActivity.newIntent(this, getString(R.string.space))
            startActivity(intent)
        }
        binding.buttonNature.setOnClickListener {
            val intent = ListPhotoActivity.newIntent(this, getString(R.string.nature))

            startActivity(intent)
        }
        binding.buttonNight.setOnClickListener {
            val intent = ListPhotoActivity.newIntent(this, getString(R.string.night))

            startActivity(intent)
        }
        binding.buttonMorning.setOnClickListener {
            val intent = ListPhotoActivity.newIntent(this, getString(R.string.morning))

            startActivity(intent)
        }
        binding.buttonSunset.setOnClickListener {
            val intent = ListPhotoActivity.newIntent(this, getString(R.string.sunset))

            startActivity(intent)
        }
        binding.buttonWinter.setOnClickListener {
            val intent = ListPhotoActivity.newIntent(this, getString(R.string.winter))

            startActivity(intent)
        }
        binding.buttonCity.setOnClickListener {
            val intent = ListPhotoActivity.newIntent(this, getString(R.string.city))

            startActivity(intent)
        }
        binding.buttonForest.setOnClickListener {
            val intent = ListPhotoActivity.newIntent(this, getString(R.string.forest))

            startActivity(intent)
        }
        binding.buttonRain.setOnClickListener {
            val intent = ListPhotoActivity.newIntent(this, getString(R.string.rain))

            startActivity(intent)
        }
        binding.buttonSky.setOnClickListener {
            val intent = ListPhotoActivity.newIntent(this, getString(R.string.sky))

            startActivity(intent)
        }

    }

}