package com.ilyakoz.decoratemate.data.network.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity("favorite_photo")
@Parcelize
data class Photo(

    @PrimaryKey
    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("alt_description")
    @Expose
    val alt_description: String? = null,
    @SerializedName("urls")
    @Expose
    @Embedded
    val urls: Urls? = null


) : Parcelable