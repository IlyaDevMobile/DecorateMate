package com.ilyakoz.decoratemate.data.network.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class PhotoResponseDto(
    @SerializedName("results")
    @Expose
    val photoInfoDto: List<PhotoInfoDto>? = null
) : Parcelable


