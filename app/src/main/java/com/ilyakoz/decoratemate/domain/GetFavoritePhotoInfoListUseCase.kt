package com.ilyakoz.decoratemate.domain

class GetFavoritePhotoInfoListUseCase(private val photoRepository: PhotoRepository) {

    operator fun invoke() = photoRepository.getFavoritePhotoInfoList()

}