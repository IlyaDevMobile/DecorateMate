package com.ilyakoz.decoratemate.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.ilyakoz.decoratemate.data.database.AppDatabase
import com.ilyakoz.decoratemate.data.mapper.PhotoMapper
import com.ilyakoz.decoratemate.data.network.api.ApiFactory
import com.ilyakoz.decoratemate.domain.PhotoRepository
import com.ilyakoz.decoratemate.domain.model.PhotoInfo
import com.ilyakoz.decoratemate.domain.model.PhotoResponse
import com.ilyakoz.decoratemate.domain.model.Urls
import java.io.IOException

class PhotoRepositoryImpl(private val application: Application) : PhotoRepository {

    private val photoInfoDao = AppDatabase.getInstance(application).photoDao()


    private val mapper = PhotoMapper()

    override suspend fun addFavoritePhoto(photoInfo: PhotoInfo) {
        val dbModel = mapper.mapDtoToDbModel(photoInfo)
        photoInfoDao.addPhoto(dbModel)
    }

    override suspend fun deleteFavoritePhoto(photoId: String) {
        photoInfoDao.deletePhoto(photoId)
    }


    override fun getFavoritePhotoInfoList(): LiveData<List<PhotoInfo>> {
        return photoInfoDao.getPhotoList().map { dbModelList ->
            dbModelList.mapNotNull { dbModel ->
                mapper.mapDbModelToEntity(dbModel)
            }
        }
    }



    override suspend fun getFavoritePhotoInfo(photoId: String): PhotoInfo? {
        val dbModel = photoInfoDao.getPhoto(photoId)
        return mapper.mapDbModelToEntity(dbModel)
    }


    override suspend fun loadPhoto(page: Int, query: String): PhotoResponse {
        try {
            val response = ApiFactory.apiService?.loadPhoto(page = page, query = query)
            if (response != null && response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    val photoInfoList = responseBody.photoInfoDto?.map { dto ->
                        PhotoInfo(
                            id = dto.id,
                            alt_description = dto.alt_description,
                            urls = dto.urlsDto?.let { urlsDto ->
                                Urls(
                                    regular = urlsDto.regular
                                )
                            }
                        )
                    }
                    return PhotoResponse(photoInfoList)
                }
            }
        } catch (e: Exception) {
            throw IOException("Failed to load photos", e)
        }
        throw IOException("Failed to load photos")
    }
}