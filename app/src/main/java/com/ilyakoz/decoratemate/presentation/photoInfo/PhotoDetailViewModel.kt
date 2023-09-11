package com.ilyakoz.decoratemate.presentation.photoInfo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ilyakoz.decoratemate.data.database.AppDatabase
import com.ilyakoz.decoratemate.data.network.model.Photo

class PhotoDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application).photoDao()

    fun getFavoritePhoto(photoId: String): LiveData<Photo> {
        return db.getPhoto(photoId)
    }

    suspend fun insertPhoto(photo: Photo){
        val result = db.addPhoto(photo)
    }

    suspend fun removePhoto(photoId: String){
        val result = db.deletePhoto(photoId)
    }

}