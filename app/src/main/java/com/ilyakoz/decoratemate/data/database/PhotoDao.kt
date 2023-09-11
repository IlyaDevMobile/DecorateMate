package com.ilyakoz.decoratemate.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ilyakoz.decoratemate.data.network.model.Photo


@Dao
interface PhotoDao {

    @Query("SELECT * FROM favorite_photo")
    fun getPhotoList(): LiveData<List<Photo>>

    @Query("SELECT * FROM favorite_photo WHERE id=:photoId LIMIT 1")
    fun getPhoto(photoId: String): LiveData<Photo>

    @Insert
    suspend fun addPhoto(photo: Photo)

    @Query("DELETE FROM favorite_photo WHERE id=:photoId")
    suspend fun deletePhoto(photoId: String)
}