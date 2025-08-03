//package com.joohyeong.architecture_pattern_study.data
//
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import kotlinx.serialization.json.Json
//import okhttp3.MediaType.Companion.toMediaType
//import retrofit2.Retrofit
//import retrofit2.converter.kotlinx.serialization.asConverterFactory
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//object ApiModule {
//
//    private const val BASE_URL = "http://api.koreafilm.or.kr"
//    private val mediaType = "application/json; charset=UTF8".toMediaType()
//
//    @Provides
//    @Singleton
//    fun provideJson(): Json = Json {
//        ignoreUnknownKeys = true
//    }
//
//    @Provides
//    @Singleton
//    fun provideRetrofit(json: Json): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(json.asConverterFactory(mediaType))
//            .build()
//    }
//}