package com.viktor_zet.photogallery_p5.app

import android.app.Application
import com.viktor_zet.photogallery_p5.repository.PreferencesRepository

class PhotoGalleryApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        PreferencesRepository.initialize(this)
    }
}