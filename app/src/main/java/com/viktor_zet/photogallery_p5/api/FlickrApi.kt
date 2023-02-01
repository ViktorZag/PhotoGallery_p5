package com.viktor_zet.photogallery_p5.api

import retrofit2.http.GET

interface FlickrApi {

    @GET("/")
    suspend fun fetchContents(): String
}