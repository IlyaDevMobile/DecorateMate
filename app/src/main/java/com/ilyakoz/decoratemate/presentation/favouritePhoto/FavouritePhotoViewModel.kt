package com.ilyakoz.decoratemate.presentation.favouritePhoto

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ilyakoz.decoratemate.data.database.AppDatabase
import com.ilyakoz.decoratemate.data.network.model.Photo

class FavouritePhotoViewModel(application: Application) : AndroidViewModel(application) {

    fun getFavoritePhoto(): LiveData<List<Photo>> {
        return db.getPhotoList()
    }


    private val db = AppDatabase.getInstance(application).photoDao()

}