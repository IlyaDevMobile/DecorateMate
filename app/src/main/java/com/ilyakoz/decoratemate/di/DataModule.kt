package com.ilyakoz.decoratemate.di

import android.app.Application
import com.ilyakoz.decoratemate.data.database.AppDatabase
import com.ilyakoz.decoratemate.data.database.PhotoInfoDao
import com.ilyakoz.decoratemate.data.repository.PhotoRepositoryImpl
import com.ilyakoz.decoratemate.domain.PhotoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun providePhotoInfoDao(appDatabase: AppDatabase) : PhotoInfoDao{
        return appDatabase.photoDao()
    }

    @Provides
    @Singleton
    fun providePhotoInfoRepository(application: Application) : PhotoRepository{
        return PhotoRepositoryImpl(application)
    }

}