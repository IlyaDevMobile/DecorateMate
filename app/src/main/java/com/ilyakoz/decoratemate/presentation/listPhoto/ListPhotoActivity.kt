package com.ilyakoz.decoratemate.presentation.listPhoto

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import com.ilyakoz.decoratemate.R
import com.ilyakoz.decoratemate.databinding.ActivityMainBinding
import com.ilyakoz.decoratemate.domain.model.PhotoInfo
import com.ilyakoz.decoratemate.presentation.adapter.PhotoAdapter
import com.ilyakoz.decoratemate.presentation.favouritePhoto.FavouritePhotoActivity
import com.ilyakoz.decoratemate.presentation.photoInfo.PhotoDetailActivity
import com.ilyakoz.decoratemate.presentation.settings.SettingsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ListPhotoActivity : AppCompatActivity() {

    private lateinit var adapter: PhotoAdapter
    private val viewModel: ListPhotoViewModel by viewModels()

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        adapter = PhotoAdapter()
        binding.recyclerViewPhoto.adapter = adapter
        binding.recyclerViewPhoto.layoutManager = GridLayoutManager(this, 2)

        val query = intent.getStringExtra(EXTRA_CATEGORY)

        lifecycleScope.launch {
            viewModel.photoFlow.collect { photoList ->
                if (photoList != null) {
                    adapter.submitList(photoList)
                }
            }
        }
        adapter.onPhotoItemClickListener = {
            val intent = PhotoDetailActivity.newIntent(
                this,
                PhotoInfo(id = it.id, alt_description = it.alt_description, urls = it.urls)
            )
            startActivity(intent)
        }


        viewModel.viewModelScope.launch {
            viewModel.loadingFlow.collect { isLoading ->
                binding.progressBarLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }


        viewModel.loadPhoto(query.toString())

        adapter.onReachEndListener = object : PhotoAdapter.OnReachEndListener {
            override fun onReachEnd() {
                viewModel.loadPhoto(query.toString())
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favouriteMovies -> {
                val intent = FavouritePhotoActivity.newIntent(this)
                startActivity(intent)
                true
            }

            R.id.settings -> {
                val intent = SettingsActivity.newIntent(this)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
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

