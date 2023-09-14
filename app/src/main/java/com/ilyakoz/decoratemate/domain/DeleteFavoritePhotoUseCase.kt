package com.ilyakoz.decoratemate.domain

class DeleteFavoritePhotoUseCase(private val photoRepository: PhotoRepository) {

    suspend fun deleteFavoritePhoto(photoId: String){
        photoRepository.deleteFavoritePhoto(photoId)
    }
}