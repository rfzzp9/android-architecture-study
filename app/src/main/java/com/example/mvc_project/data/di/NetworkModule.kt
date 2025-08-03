package com.example.mvc_project.data.di

import com.example.mvc_project.data.network.ApiService
import com.example.mvc_project.data.network.RetrofitInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideApiService(): ApiService = RetrofitInstance.getInstance().create(ApiService::class.java)
}