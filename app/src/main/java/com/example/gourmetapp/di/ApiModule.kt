package com.example.gourmetapp.di

import com.example.gourmetapp.network.ApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

  @Provides
  @Singleton
  internal fun providesMoshi(): Moshi {
    return Moshi.Builder()
      .add(KotlinJsonAdapterFactory())
      .build()
  }

  @Provides
  @Singleton
  internal fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()
  }

  @Provides
  @Singleton
  internal fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply {
      this.level = HttpLoggingInterceptor.Level.BODY
    }
  }

  @Provides
  @Singleton
  internal fun providesMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory {
    return MoshiConverterFactory.create(moshi)
      .asLenient()
  }

  @Provides
  @Singleton
  internal fun providesRetrofit(
    moshiConverterFactory: MoshiConverterFactory,
    okHttpClient: OkHttpClient
  ): Retrofit {
    return Retrofit.Builder().addConverterFactory(moshiConverterFactory)
      .baseUrl(BASE_URL)
      .client(okHttpClient)
      .build()
  }

  @Provides
  @Singleton
  internal fun provideApiService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
  }

  companion object {
    const val BASE_URL = "https://recruitment-task.futuremind.dev/"
  }
}