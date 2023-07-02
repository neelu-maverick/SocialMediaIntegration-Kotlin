package com.example.socialmediaintegration_kotlin.di

import com.example.socialmediaintegration_kotlin.api.ApiInterface

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
class Network {
    val BASE_URL  = "https://api.linkedin.com/v2/"
    @Singleton
    @Provides
    fun providesRetrofit() : Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideList(retrofit: Retrofit) : ApiInterface{
        return retrofit.create(ApiInterface::class.java)
    }

}