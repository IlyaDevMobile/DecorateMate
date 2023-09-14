package com.ilyakoz.decoratemate.di

import com.ilyakoz.decoratemate.domain.AddFavoritePhotoUseCase
import com.ilyakoz.decoratemate.domain.DeleteFavoritePhotoUseCase
import com.ilyakoz.decoratemate.domain.GetFavoritePhotoInfoUseCase
import com.ilyakoz.decoratemate.domain.GetFavoritePhotoInfoListUseCase
import com.ilyakoz.decoratemate.domain.LoadPhotoUseCase
import com.ilyakoz.decoratemate.domain.PhotoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideAddFavoritePhotoUseCase(photoRepository: PhotoRepository): AddFavoritePhotoUseCase {
        return AddFavoritePhotoUseCase(photoRepository)
    }

    @Provides
    fun provideDeleteFavoritePhotoUseCase(photoRepository: PhotoRepository): DeleteFavoritePhotoUseCase {
        return DeleteFavoritePhotoUseCase(photoRepository)
    }

    @Provides
    fun provideGetFavoritePhotoInfo(photoRepository: PhotoRepository): GetFavoritePhotoInfoUseCase {
        return GetFavoritePhotoInfoUseCase(photoRepository)
    }

    @Provides
    fun provideGetFavoritePhotoInfoList(photoRepository: PhotoRepository): GetFavoritePhotoInfoListUseCase {
        return GetFavoritePhotoInfoListUseCase(photoRepository)
    }

    @Provides
    fun provideLoadDataUseCase(repository: PhotoRepository): LoadPhotoUseCase {
        return LoadPhotoUseCase(repository)
    }


}