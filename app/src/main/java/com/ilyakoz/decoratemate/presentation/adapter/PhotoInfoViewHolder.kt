package com.ilyakoz.decoratemate.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ilyakoz.decoratemate.databinding.ItemPhotoBinding
import com.ilyakoz.decoratemate.domain.model.PhotoInfo

class PhotoInfoViewHolder(private val binding: ItemPhotoBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: PhotoInfo) {
        binding.descriptionPhoto.text = item.alt_description
        Glide.with(itemView)
            .load(item.urls?.regular)
            .into(binding.photoItemIv)
    }

}