package com.ilyakoz.decoratemate.domain

import com.ilyakoz.decoratemate.domain.model.PhotoInfo

class GetFavoritePhotoInfoUseCase(private val photoRepository: PhotoRepository) {

    suspend fun getPhotoInfo(photoId : String): PhotoInfo?{
        return photoRepository.getFavoritePhotoInfo(photoId)
    }
}