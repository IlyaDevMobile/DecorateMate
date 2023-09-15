package com.ilyakoz.decoratemate.presentation.favouritePhoto

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ilyakoz.decoratemate.databinding.ActivityFavouritePhotoBinding
import com.ilyakoz.decoratemate.domain.model.PhotoInfo
import com.ilyakoz.decoratemate.presentation.adapter.PhotoAdapter
import com.ilyakoz.decoratemate.presentation.photoInfo.PhotoDetailActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavouritePhotoActivity : AppCompatActivity() {


    private val binding by lazy {
        ActivityFavouritePhotoBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: FavouritePhotoViewModel


    private lateinit var adapter: PhotoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide() // Скрываем Action Bar
        adapter = PhotoAdapter()
        binding.recyclerViewPhoto.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerViewPhoto.adapter = adapter
        viewModel = ViewModelProvider(this)[FavouritePhotoViewModel::class.java]
        viewModel.photoList.observe(this, Observer {
            adapter.submitList(it)
        })
        adapter.onPhotoItemClickListener
        adapter.onPhotoItemClickListener = {
            val intent = PhotoDetailActivity.newIntent(
                this,
                PhotoInfo(id = it.id, alt_description = it.alt_description, urls = it.urls)
            )
            startActivity(intent)
        }
    }


    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, FavouritePhotoActivity::class.java)
        }

    }
}