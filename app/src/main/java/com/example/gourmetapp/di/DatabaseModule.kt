package com.example.gourmetapp.di

import android.app.Application
import androidx.room.Room
import com.example.gourmetapp.data.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.annotation.AnnotationRetention.RUNTIME

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

  @ApplicationScope
  @Provides
  @Singleton
  fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}

@Retention(RUNTIME)
@Qualifier
annotation class ApplicationScope