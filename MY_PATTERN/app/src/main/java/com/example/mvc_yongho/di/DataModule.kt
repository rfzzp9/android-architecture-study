package com.example.mvc_yongho.di

import com.example.mvc_yongho.data.datasource.MovieDataSource
import com.example.mvc_yongho.data.datasource.MovieDataSourceImpl
import com.example.mvc_yongho.domain.repository.MovieRepository
import com.example.mvc_yongho.data.repository.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindMovieDataSource(
        movieDataSourceImpl: MovieDataSourceImpl
    ): MovieDataSource

    @Binds
    fun bindMovieRepository(
        movieRepositoryImpl: MovieRepositoryImpl
    ): MovieRepository

}
