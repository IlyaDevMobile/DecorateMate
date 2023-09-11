package com.ilyakoz.decoratemate.presentation.favouritePhoto

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ilyakoz.decoratemate.data.network.model.Photo
import com.ilyakoz.decoratemate.databinding.ActivityFavouritePhotoBinding
import com.ilyakoz.decoratemate.presentation.adapter.PhotoAdapter
import com.ilyakoz.decoratemate.presentation.photoInfo.PhotoDetailActivity

class FavouritePhotoActivity : AppCompatActivity() {


    private val binding by lazy {
        ActivityFavouritePhotoBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: FavouritePhotoViewModel


    private lateinit var adapter: PhotoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        adapter = PhotoAdapter()
        binding.recyclerViewPhoto.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerViewPhoto.adapter = adapter
        viewModel = ViewModelProvider(this)[FavouritePhotoViewModel::class.java]
        viewModel.getFavoritePhoto().observe(this, Observer {
            adapter.submitList(it)
        })
        adapter.onPhotoItemClickListener
        adapter.onPhotoItemClickListener = {
            val intent = PhotoDetailActivity.newIntent(
                this,
                Photo(id = it.id, alt_description = it.alt_description, urls = it.urls)
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