package com.ilyakoz.decoratemate.presentation.listPhoto

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import com.ilyakoz.decoratemate.R
import com.ilyakoz.decoratemate.data.network.model.Photo
import com.ilyakoz.decoratemate.databinding.ActivityMainBinding
import com.ilyakoz.decoratemate.presentation.adapter.PhotoAdapter
import com.ilyakoz.decoratemate.presentation.favouritePhoto.FavouritePhotoActivity
import com.ilyakoz.decoratemate.presentation.photoInfo.PhotoDetailActivity
import kotlinx.coroutines.launch

class ListPhotoActivity : AppCompatActivity() {

    private lateinit var viewModel: ListPhotoViewModel

    private lateinit var adapter: PhotoAdapter


    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        adapter = PhotoAdapter()
        binding.recyclerViewPhoto.adapter = adapter
        binding.recyclerViewPhoto.layoutManager = GridLayoutManager(this, 2)
        viewModel = ViewModelProvider(this)[ListPhotoViewModel::class.java]
        val query = intent.getStringExtra(EXTRA_CATEGORY)
        viewModel.photo.observe(this) { photoResponse ->
            if (photoResponse != null) {
                adapter.submitList(photoResponse.photo)
                Log.d("ListPhotoActivity", photoResponse.toString())
                Log.d("ListPhotoActivity", "LiveData содержит данные: ${photoResponse.photo}")
            }
        }
        viewModel.viewModelScope.launch {
            query?.let { viewModel.loadPhoto(it) }
        }
        viewModel.loading.observe(this) { isloading ->
            if (isloading) {
                binding.progressBarLoading.visibility = View.VISIBLE
            } else {
                binding.progressBarLoading.visibility = View.GONE
            }
        }
        adapter.onReachEndListener = object : PhotoAdapter.OnReachEndListener {
            override fun onReachEnd() {
                viewModel.viewModelScope.launch {
                    query?.let { viewModel.loadPhoto(it) }
                }
            }
        }
        adapter.onPhotoItemClickListener = {
            val intent = PhotoDetailActivity.newIntent(
                this,
                Photo(id = it.id, alt_description = it.alt_description, urls = it.urls)
            )
            startActivity(intent)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favouriteMovies -> {
                val intent = FavouritePhotoActivity.newIntent(this)
                startActivity(intent)
                return true
            }

            R.id.settings -> {
                // Обработка выбора "Настройки"
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearPhotos()
    }


    companion object {

        private const val EXTRA_CATEGORY = "category"

        fun newIntent(context: Context, query: String): Intent {
            val intent = Intent(context, ListPhotoActivity::class.java)
            intent.putExtra(EXTRA_CATEGORY, query)
            return intent
        }

    }

}
