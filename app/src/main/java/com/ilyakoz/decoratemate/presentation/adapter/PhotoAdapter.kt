package com.ilyakoz.decoratemate.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ilyakoz.decoratemate.databinding.ItemPhotoBinding
import com.ilyakoz.decoratemate.data.network.model.Photo

class PhotoAdapter :
    ListAdapter<Photo, PhotoInfoViewHolder>(PhotoDiffCallBack) {


    var onPhotoItemClickListener: ((Photo) -> Unit)? = null
    var onReachEndListener: OnReachEndListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoInfoViewHolder {
        return PhotoInfoViewHolder(
            ItemPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PhotoInfoViewHolder, position: Int) {
        Log.d("onBindViewHolder", "onBindViewHolder $position")
        val photoItem = getItem(position)
        holder.bind(photoItem)
        if (position >= itemCount - 10 && onReachEndListener != null ) {
            onReachEndListener?.onReachEnd()
        }
        holder.itemView.setOnClickListener {
            onPhotoItemClickListener?.invoke(photoItem)
        }
    }

    interface OnReachEndListener {
        fun onReachEnd()

    }




}