package com.ilyakoz.decoratemate.data.network.api

import com.ilyakoz.decoratemate.data.network.model.PhotoResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("photos")
    suspend fun loadPhoto(
        @Query(CLIENT_ID) client_id : String = "vMzMhEYMiBxRxcmOO7QLgZcTcQyJKDy7g0zasdfItrA",
        @Query(PAGE) page : Int,
        @Query(PER_PAGE) per_page : Int = 30,
        @Query(ORIENTATION) orientation : String = СATEGORY_ORIENTATION,
        @Query(СATEGORY_PHOTO) query : String
    ) : Response<PhotoResponseDto>



    companion object {
        private const val CLIENT_ID = "client_id"
        private const val PER_PAGE  = "per_page"
        private const val PAGE  = "page"
        private const val ORIENTATION  = "orientation"
        private const val СATEGORY_PHOTO= "query"

        private const val СATEGORY_ORIENTATION = "portrait"




    }
}