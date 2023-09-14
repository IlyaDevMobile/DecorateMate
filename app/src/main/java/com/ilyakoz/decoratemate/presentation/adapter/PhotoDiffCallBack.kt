package com.ilyakoz.decoratemate.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.ilyakoz.decoratemate.domain.model.PhotoInfo

object PhotoDiffCallBack:DiffUtil.ItemCallback<PhotoInfo>() {
    override fun areItemsTheSame(oldItem: PhotoInfo, newItem: PhotoInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PhotoInfo, newItem: PhotoInfo): Boolean {
        return oldItem == newItem
    }
}