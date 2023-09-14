package com.ilyakoz.decoratemate.domain

import androidx.lifecycle.LiveData
import com.ilyakoz.decoratemate.domain.model.PhotoInfo
import com.ilyakoz.decoratemate.domain.model.PhotoResponse

interface PhotoRepository {

    suspend fun addFavoritePhoto(photoInfo: PhotoInfo)
    suspend fun deleteFavoritePhoto(photoId: String)

    suspend fun loadPhoto(page: Int, query: String) : PhotoResponse
    suspend fun getFavoritePhotoInfo(photoId : String): PhotoInfo?

    fun getFavoritePhotoInfoList(): LiveData<List<PhotoInfo>>

}