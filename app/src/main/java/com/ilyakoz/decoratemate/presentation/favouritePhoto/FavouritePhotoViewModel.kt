package com.ilyakoz.decoratemate.presentation.favouritePhoto

import androidx.lifecycle.ViewModel
import com.ilyakoz.decoratemate.domain.GetFavoritePhotoInfoListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class FavouritePhotoViewModel @Inject constructor(
    private val getFavoritePhotoInfoListUseCase: GetFavoritePhotoInfoListUseCase
) : ViewModel() {


    val photoList = getFavoritePhotoInfoListUseCase.invoke()



}