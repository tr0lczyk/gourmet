package com.example.gourmetapp.di

import android.app.Application
import androidx.room.Room
import com.example.gourmetapp.data.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

  @Provides
  @Singleton
  fun provideDatabase(
    app: Application
  ) = Room.databaseBuilder(app, NewsDatabase::class.java, "news_database")
    .fallbackToDestructiveMigration()
    .build()

  @Provides
  fun provideNewsDao(db: NewsDatabase) = db.newsDao()
}