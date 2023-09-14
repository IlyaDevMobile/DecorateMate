package com.ilyakoz.decoratemate.data.database.model

import androidx.room.TypeConverter
import com.google.gson.Gson


class UrlsDbModelConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromUrlsDbModel(urlsDbModel: UrlsDbModel?): String? {
        return gson.toJson(urlsDbModel)
    }

    @TypeConverter
    fun toUrlsDbModel(urlsDbModelJson: String?): UrlsDbModel? {
        return gson.fromJson(urlsDbModelJson, UrlsDbModel::class.java)
    }
}


