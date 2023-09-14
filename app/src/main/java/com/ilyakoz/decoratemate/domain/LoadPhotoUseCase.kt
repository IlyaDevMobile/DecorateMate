package com.ilyakoz.decoratemate.domain

import com.ilyakoz.decoratemate.domain.model.PhotoResponse

class LoadPhotoUseCase(private val repository: PhotoRepository) {

    suspend fun loadPhoto(page: Int, query: String): PhotoResponse {
        return repository.loadPhoto(page, query)
    }
}