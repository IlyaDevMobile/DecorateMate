package com.ilyakoz.decoratemate.data.mapper

import com.ilyakoz.decoratemate.data.database.model.PhotoInfoDbModel
import com.ilyakoz.decoratemate.data.database.model.UrlsDbModel
import com.ilyakoz.decoratemate.domain.model.PhotoInfo
import com.ilyakoz.decoratemate.domain.model.Urls

class PhotoMapper {


    fun mapDtoToDbModel(dto: PhotoInfo) = PhotoInfoDbModel(
        id = dto.id,
        alt_description = dto.alt_description,
        urlsDbModel = UrlsDbModel(dto.urls?.regular ?: "")
    )

    fun mapDbModelToEntity(dbModel: PhotoInfoDbModel?): PhotoInfo? {
        return dbModel?.let {
            PhotoInfo(
                id = it.id,
                alt_description = it.alt_description,
                urls = it.urlsDbModel?.regular?.let { regularUrl ->
                    Urls(
                        regular = regularUrl
                    )
                }
            )
        }
    }

}



