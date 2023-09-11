package com.ilyakoz.decoratemate.data.network.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class PhotoResponse(
    @SerializedName("results")
    @Expose
    val photo: List<Photo>? = null
) : Parcelable


