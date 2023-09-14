package com.ilyakoz.decoratemate.data.network.model

import android.os.Parcelable

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotoInfoDto(

    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("alt_description")
    @Expose
    val alt_description: String? = null,
    @SerializedName("urls")
    @Expose
    val urlsDto: UrlsDto? = null


) : Parcelable