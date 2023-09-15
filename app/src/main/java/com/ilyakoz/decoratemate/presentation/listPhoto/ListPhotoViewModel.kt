package com.ilyakoz.decoratemate.presentation.listPhoto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilyakoz.decoratemate.domain.LoadPhotoUseCase
import com.ilyakoz.decoratemate.domain.model.PhotoInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListPhotoViewModel @Inject constructor(
    private val loadPhotoUseCase: LoadPhotoUseCase
) : ViewModel() {

    private val _photos = MutableLiveData<List<PhotoInfo>>()
    val photoLiveData: LiveData<List<PhotoInfo>> = _photos

    private val _loading = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> = _loading

    private var page: Int = 1

    fun loadPhoto(query: String) {
        if (_loading.value == true) {
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
            } catch (_: Exception) {

            } finally {
                _loading.value = false
            }
        }
    }

}




