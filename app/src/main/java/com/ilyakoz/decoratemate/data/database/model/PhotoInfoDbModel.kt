package com.ilyakoz.decoratemate.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("favorite_photo")
data class PhotoInfoDbModel(
    @PrimaryKey
    val id: String,
    val alt_description: String? = null,
    val urlsDbModel: UrlsDbModel? = null
)