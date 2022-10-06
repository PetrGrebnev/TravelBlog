package com.example.travelblog.di

import android.util.Log
import com.example.travelblog.network.TravelNetworkService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providerBaseUrl(): String = "https://rsttur.ru/api/base-app/"

    private fun getOkHttpClient() =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor { message ->
                    Log.d("OK HTTP", message)
                }.apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()

    @Provides
    @Singleton
    fun providerRetrofit(BASE_URL: String) : Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(getOkHttpClient())
        .build()

    @Provides
    @Singleton
    fun providerNetworkService(retrofit: Retrofit): TravelNetworkService =
        retrofit.create(TravelNetworkService::class.java)
}