package com.ilyakoz.decoratemate.presentation.photoInfo

import android.app.WallpaperManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.ilyakoz.decoratemate.R
import com.ilyakoz.decoratemate.databinding.ActivityPhotoDetailBinding
import com.ilyakoz.decoratemate.domain.model.PhotoInfo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.IOException
import kotlin.math.log

@AndroidEntryPoint
class PhotoDetailActivity : AppCompatActivity() {


    private val binding by lazy {
        ActivityPhotoDetailBinding.inflate(layoutInflater)
    }

    private val viewModel: PhotoDetailViewModel by viewModels()

    private var photoInfo: PhotoInfo? = null

    private val favoriteOff by lazy {
        ContextCompat.getDrawable(this, R.drawable.baseline_favorite_border_24)
    }
    private val favoriteOn by lazy {
        ContextCompat.getDrawable(this, R.drawable.baseline_favorite_24)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide() // Скрываем Action Bar
        photoInfo = intent.getParcelableExtra<PhotoInfo?>(EXTRA_PHOTO)
        checkingFavorites(photoInfo)
        setupUi()

    }

    private fun checkingFavorites(photoInfo: PhotoInfo?) {
        viewModel.viewModelScope.launch {
            val isFavorite = viewModel.getFavoritePhotoInfoSafe(photoInfo?.id.toString())
            if (isFavorite == null) {
                binding.buttonFavorite.setImageDrawable(favoriteOff)
            } else {
                binding.buttonFavorite.setImageDrawable(favoriteOn)
            }
        }
    }

    private fun setupUi() {
        loadPhotoFromActivityList(photoInfo)
        binding.buttonLoad.setOnClickListener {
            saveImageInGallery()
        }

        binding.buttonApply.setOnClickListener {
            showApplyDialog()
        }
        binding.buttonFavorite.setOnClickListener {
            saveImageInFavorite(photoInfo)

        }
    }

    private fun showApplyDialog() {
        val options = arrayOf(MAIN_SCREEN, LOCK_SCREEN, MAIN_SCREEN_AND_LOCK_SCREEN)
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.select_an_action))
            .setItems(options) { _, which ->
                when (which) {
                    0 -> applyWallpaper(applyToSystem = true, applyToLockScreen = false)
                    1 -> applyWallpaper(applyToSystem = false, applyToLockScreen = true)
                    2 -> applyWallpaper(applyToSystem = true, applyToLockScreen = true)
                }
            }
            .create()
            .show()
    }


    private fun saveImageInFavorite(photoInfo: PhotoInfo?) {
        viewModel.viewModelScope.launch {
            val isFavorite = viewModel.getFavoritePhotoInfoSafe(photoInfo?.id ?: "")
            if (isFavorite == null) {
                binding.buttonFavorite.setImageDrawable(favoriteOn)
                viewModel.addFavoritePhoto(photoInfo)
                showToast(getString(R.string.photo_add_in_favorite))
            } else {
                binding.buttonFavorite.setImageDrawable(favoriteOff)
                viewModel.deleteFavouritePhoto(photoInfo?.id ?: "")
                showToast(getString(R.string.photo_remove_from_favorite))
            }
        }
    }

    private fun saveImageInGallery() {
        val drawable = binding.clickPhoto.drawable
        if (drawable is BitmapDrawable) {
            val bitmap = drawable.bitmap
            val photoInfo = intent.getParcelableExtra<PhotoInfo>(EXTRA_PHOTO)
            photoInfo?.alt_description?.let { description ->
                saveImageToGallery(bitmap, description)
            }
        }
    }

    private fun loadPhotoFromActivityList(photoInfo: PhotoInfo?) {
        Glide.with(this)
            .load(photoInfo?.urls?.regular)
            .into(binding.clickPhoto)
        binding.description.text = photoInfo?.alt_description.toString()
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun applyWallpaper(applyToSystem: Boolean, applyToLockScreen: Boolean) {
        val drawable = binding.clickPhoto.drawable
        if (drawable is BitmapDrawable) {
            val bitmap = drawable.bitmap
            val wallpaperManager = WallpaperManager.getInstance(applicationContext)
            try {
                var flags = 0
                if (applyToSystem) {
                    flags = flags or WallpaperManager.FLAG_SYSTEM
                }
                if (applyToLockScreen) {
                    flags = flags or WallpaperManager.FLAG_LOCK
                }
                wallpaperManager.setBitmap(bitmap, null, true, flags)
                showToast("Wallpaper successfully installed")
            } catch (e: IOException) {
                showToast(getString(R.string.an_error_occurred_while_installing_the_wallpaper))
            }
        }
    }


    private fun saveImageToGallery(bitmap: Bitmap, title: String) {
        val fileName = "$title.jpg"
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.WIDTH, bitmap.width)
            put(MediaStore.Images.Media.HEIGHT, bitmap.height)
        }
        val resolver = contentResolver
        val imageUri =
            resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        try {
            imageUri?.let { uri ->
                resolver.openOutputStream(uri)?.use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    outputStream.close()
                    showToast(getString(R.string.image_saved_to_gallery))
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            showToast(getString(R.string.failed_to_save_image))
        }
    }


    companion object {


        private const val MAIN_SCREEN = "Main Screen"
        private const val LOCK_SCREEN = "Lock Screen"
        private const val MAIN_SCREEN_AND_LOCK_SCREEN = "Main Screen and Lock Screen"

        private const val EXTRA_PHOTO = "PhotoInfo"

        fun newIntent(context: Context, photoInfo: PhotoInfo): Intent {
            val intent = Intent(context, PhotoDetailActivity::class.java)
            intent.putExtra(EXTRA_PHOTO, photoInfo)
            return intent
        }

    }
}