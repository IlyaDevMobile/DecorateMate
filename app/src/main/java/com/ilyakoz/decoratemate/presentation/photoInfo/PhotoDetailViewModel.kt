package com.ilyakoz.decoratemate.presentation.photoInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilyakoz.decoratemate.domain.AddFavoritePhotoUseCase
import com.ilyakoz.decoratemate.domain.DeleteFavoritePhotoUseCase
import com.ilyakoz.decoratemate.domain.GetFavoritePhotoInfoUseCase
import com.ilyakoz.decoratemate.domain.model.PhotoInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
    private val addFavoritePhotoUseCase: AddFavoritePhotoUseCase,
    private val deleteFavoritePhotoUseCase: DeleteFavoritePhotoUseCase,
    private val getFavoritePhotoInfoUseCase: GetFavoritePhotoInfoUseCase,
) : ViewModel() {


    suspend fun addFavoritePhoto(photoInfo: PhotoInfo?) {
        viewModelScope.launch {
            photoInfo?.let { addFavoritePhotoUseCase.addFavoritePhoto(it) }
        }
    }


    suspend fun deleteFavouritePhoto(photoId: String) {
        viewModelScope.launch {
            deleteFavoritePhotoUseCase.deleteFavoritePhoto(photoId)
        }
    }

    suspend fun getFavoritePhotoInfo(photoId: String): PhotoInfo? {
        return getFavoritePhotoInfoUseCase.getPhotoInfo(photoId)
    }

    suspend fun getFavoritePhotoInfoSafe(photoId: String): PhotoInfo? {
        return viewModelScope.runCatching {
            getFavoritePhotoInfo(photoId)
        }.getOrNull()
    }


}