package com.ilyakoz.decoratemate.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class PhotoInfo(
    val id: String,
    val alt_description: String? = null,
    val urls: Urls? = null
) : Parcelable