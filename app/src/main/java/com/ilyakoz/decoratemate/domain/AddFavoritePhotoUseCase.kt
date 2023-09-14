package com.ilyakoz.decoratemate.domain

import com.ilyakoz.decoratemate.domain.model.PhotoInfo

class AddFavoritePhotoUseCase(private val photoRepository: PhotoRepository) {

    suspend fun addFavoritePhoto(photoInfo: PhotoInfo){
        photoRepository.addFavoritePhoto(photoInfo)
    }
}