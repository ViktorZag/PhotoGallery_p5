package com.viktor_zet.photogallery_p5.api

import com.viktor_zet.photogallery_p5.model.FlickrResponse
import retrofit2.http.GET

private const val API_KEY = "a271fba6aab628f0c586b214ba3e625a"

interface FlickrApi {

    @GET("services/rest/?method=flickr.interestingness.getList"+
    "&api_key=$API_KEY"+
    "&format=json"+
    "&nojsoncallback=1"+
    "&extras=url_s")
    suspend fun fetchPhotos(): FlickrResponse
}