package com.ilyakoz.decoratemate.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.ilyakoz.decoratemate.data.network.model.Photo

object PhotoDiffCallBack:DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem == newItem
    }
}