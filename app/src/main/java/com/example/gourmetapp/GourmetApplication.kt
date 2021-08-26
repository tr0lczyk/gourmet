package com.example.gourmetapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class GourmetApplication : Application() {

  override fun onCreate() {
    super.onCreate()
    Timber.plant(Timber.DebugTree())
    instance = this
  }

  companion object {
    lateinit var instance: GourmetApplication
      private set
  }
}