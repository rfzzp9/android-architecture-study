package com.example.mvc_project.data.di

import com.example.mvc_project.data.MovieListRepository
import com.example.mvc_project.data.MovieListRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun bindMovieListRepository(movieListRepositoryImpl: MovieListRepositoryImpl): MovieListRepository
}