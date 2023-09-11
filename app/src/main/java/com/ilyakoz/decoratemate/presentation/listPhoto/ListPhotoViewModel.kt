package com.ilyakoz.decoratemate.presentation.listPhoto

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ilyakoz.decoratemate.data.network.api.ApiFactory
import com.ilyakoz.decoratemate.data.network.model.Photo
import com.ilyakoz.decoratemate.data.network.model.PhotoResponse

class ListPhotoViewModel : ViewModel() {


    private val _photos = MutableLiveData<PhotoResponse?>()
    val photo: MutableLiveData<PhotoResponse?>
        get() = _photos

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading


    fun clearPhotos() {
        _photos.value = null
    }


    private var page: Int = 1

    suspend fun loadPhoto(query: String) {
        val load : Boolean = _loading.value ?: false
        if (load) {
            return
        }
        try {
            _loading.postValue(true)
            val result = ApiFactory.apiService?.loadSpacePhoto(page = page, query = query)
            Log.d("ListPhotoViewModel", page.toString())

            val currentPhotoResponse = _photos.value?.photo ?: mutableListOf()
            val updatePhoto = mutableListOf<Photo>()

            updatePhoto.addAll(currentPhotoResponse)
            result?.photo?.let { updatePhoto.addAll(it) }

            val updatedResponse = PhotoResponse(updatePhoto)
            _photos.postValue(updatedResponse)
            page++

        } catch (e: Exception) {
            Log.e("ListPhotoViewModel", "Ошибка при загрузке фотографии: ${e.message}")
        } finally {
            _loading.postValue(false)
        }
    }

}