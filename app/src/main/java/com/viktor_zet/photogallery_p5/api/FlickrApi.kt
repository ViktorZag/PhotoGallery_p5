package com.viktor_zet.photogallery_p5.api

import com.viktor_zet.photogallery_p5.model.FlickrResponse
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "a271fba6aab628f0c586b214ba3e625a"

interface FlickrApi {

    @GET("services/rest/?method=flickr.interestingness.getList")
    suspend fun fetchPhotos(): FlickrResponse

    @GET("services/rest?method=flickr.photos.search")
    suspend fun searchPhotos(@Query("text") query: String): FlickrResponse
}