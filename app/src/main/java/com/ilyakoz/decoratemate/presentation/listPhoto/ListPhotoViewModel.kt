package com.ilyakoz.decoratemate.presentation.listPhoto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilyakoz.decoratemate.domain.LoadPhotoUseCase
import com.ilyakoz.decoratemate.domain.model.PhotoInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ListPhotoViewModel @Inject constructor(
    private val loadPhotoUseCase: LoadPhotoUseCase
) : ViewModel() {


    private val _photos = MutableStateFlow<List<PhotoInfo>?>(null)
    val photoFlow: StateFlow<List<PhotoInfo>?> = _photos

    private val _loading = MutableStateFlow(false)
    val loadingFlow: StateFlow<Boolean> = _loading

    private var page: Int = 1 // колечество страниц который вернет запрос



    fun loadPhoto(query: String) {
        if (_loading.value) {
            return
        }

        viewModelScope.launch {
            try {
                _loading.value = true
                val result = loadPhotoUseCase.loadPhoto(page, query)
                val currentPhotos = _photos.value.orEmpty().toMutableList()
                currentPhotos.addAll(result.photoInfo.orEmpty())
                _photos.value = currentPhotos
                page++
            } catch (e: Exception) {
            } finally {
                _loading.value = false
            }
        }
    }



}



