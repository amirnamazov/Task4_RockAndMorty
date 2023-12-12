package com.example.rockandmorty

import android.annotation.SuppressLint
import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApp : Application(), ImageLoaderFactory {

    @SuppressLint("ResourceType")
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .placeholder(R.drawable.ic_refresh)
            .build()
    }

}