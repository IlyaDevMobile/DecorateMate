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
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.ilyakoz.decoratemate.databinding.ActivityPhotoDetailBinding
import com.ilyakoz.decoratemate.domain.model.PhotoInfo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.IOException

@AndroidEntryPoint
class PhotoDetailActivity : AppCompatActivity() {


    private val binding by lazy {
        ActivityPhotoDetailBinding.inflate(layoutInflater)
    }

    private val viewModel: PhotoDetailViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val photoInfo = intent.getParcelableExtra<PhotoInfo?>(EXTRA_PHOTO)



        if (photoInfo != null) {
            Glide.with(this)
                .load(photoInfo.urls?.regular)
                .into(binding.clickPhoto)
            binding.description.text = photoInfo.alt_description.toString()
        } else {
            Log.e("PhotoDetailActivity", "PhotoInfoDto is null")
            //
        }

        binding.buttonLoad.setOnClickListener {
            val drawable = binding.clickPhoto.drawable
            if (drawable is BitmapDrawable) {
                val bitmap = drawable.bitmap
                val photoInfoDto = intent.getParcelableExtra<PhotoInfo>(EXTRA_PHOTO)
                photoInfoDto?.alt_description?.let { description ->
                    saveImageToGallery(bitmap, description)
                }
            }
        }
        binding.buttonApply.setOnClickListener {
            val drawable = binding.clickPhoto.drawable
            if (drawable is BitmapDrawable) {
                val bitmap = drawable.bitmap
                setWallpapers(bitmap)
            }
        }


        binding.buttonFavorite.setOnClickListener {
            val photoInfo = photoInfo
            if (photoInfo != null) {
                viewModel.viewModelScope.launch {
                    val isFavorite = viewModel.getFavoritePhotoInfoSafe(photoInfo.id)
                    if (isFavorite == null) {
                        viewModel.addFavoritePhoto(photoInfo)
                        Log.d("PhotoDetailActivity", "Добавил ${photoInfo.id}")
                    } else {
                        viewModel.deleteFavouritePhoto(photoInfo.id)
                        Log.d("PhotoDetailActivity", "Удалено ${photoInfo.id}")
                    }
                }
            }
        }


    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun setWallpapers(bitmap: Bitmap) {
        val wallpaperManager = WallpaperManager.getInstance(applicationContext)
        try {
            wallpaperManager.setBitmap(bitmap)
            Toast.makeText(this, "Wallpaper Changed Successfully", Toast.LENGTH_SHORT)
                .show()
        } catch (e: IOException) {
            Toast.makeText(this, "An Error Occurred", Toast.LENGTH_SHORT).show()
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
                    showToast("Image saved to gallery")
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            showToast("Failed to save image")
        }
    }


    companion object {

        private const val EXTRA_PHOTO = "PhotoInfo"

        fun newIntent(context: Context, photoInfo: PhotoInfo): Intent {
            val intent = Intent(context, PhotoDetailActivity::class.java)
            intent.putExtra(EXTRA_PHOTO, photoInfo)
            return intent
        }

    }
}