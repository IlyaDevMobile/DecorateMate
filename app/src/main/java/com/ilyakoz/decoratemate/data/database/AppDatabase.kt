package com.ilyakoz.decoratemate.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ilyakoz.decoratemate.data.database.model.PhotoInfoDbModel
import com.ilyakoz.decoratemate.data.database.model.UrlsDbModelConverter


@Database(entities = [PhotoInfoDbModel::class], version = 1, exportSchema = false)
@TypeConverters(UrlsDbModelConverter ::class)
abstract class AppDatabase : RoomDatabase() {


    abstract fun photoDao() : PhotoInfoDao
    companion object {
        private var INSTANCE: AppDatabase? = null
        private val LOCK = Any()
        private const val DB_NAME = "photoInfoDto.db"

        fun getInstance(application: Application): AppDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    AppDatabase::class.java,
                    DB_NAME
                )
                    .build()
                INSTANCE = db
                return db
            }
        }
    }
}

