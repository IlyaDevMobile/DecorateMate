package com.ilyakoz.decoratemate.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ilyakoz.decoratemate.data.database.model.PhotoInfoDbModel


@Dao
interface PhotoInfoDao {

    @Query("SELECT * FROM favorite_photo")
    fun getPhotoList(): LiveData<List<PhotoInfoDbModel>>

    @Query("SELECT * FROM favorite_photo WHERE id=:photoId LIMIT 1")
    suspend fun getPhoto(photoId: String): PhotoInfoDbModel

    @Insert
    suspend fun addPhoto(photoInfoDto: PhotoInfoDbModel)

    @Query("DELETE FROM favorite_photo WHERE id=:photoId")
    suspend fun deletePhoto(photoId: String)
}