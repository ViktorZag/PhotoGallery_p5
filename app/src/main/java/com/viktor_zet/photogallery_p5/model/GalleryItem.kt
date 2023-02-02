package com.viktor_zet.photogallery_p5.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GalleryItem(
    val title: String,
    val id: String,
   @Json(name = "url_s") val url: String
)
