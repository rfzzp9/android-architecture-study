package com.example.mvc_yongho.model.repository

import com.example.mvc_yongho.data.response.Actors
import com.example.mvc_yongho.data.response.CollectionData
import com.example.mvc_yongho.data.response.Directors
import com.example.mvc_yongho.data.response.KmdbResponse
import com.example.mvc_yongho.data.response.MovieInfo
import com.example.mvc_yongho.data.response.Plots
import com.example.mvc_yongho.data.repository.MovieRepositoryImpl
import com.example.mvc_yongho.data.datasource.MovieDataSource
import com.example.mvc_yongho.domain.model.Movie
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.verify

class MovieRepositoryImplTest {
    private lateinit var movieDataSource: MovieDataSource
    private lateinit var movieRepository: MovieRepositoryImpl

    @Before
    fun setUp() {
        movieDataSource = mock(MovieDataSource::class.java)
        movieRepository = MovieRepositoryImpl(movieDataSource)
    }

    @Test
    fun 영화_검색_성공시_MovieInfo_리스트_반환() = runTest {
        val title = "아바타"
        val movieInfo1 = MovieInfo(
            docId = "1234",
            movieId = "1234",
            movieSeq = "1234",
            title = "아바타",
            titleEng = "avatar",
            titleOrg = "avatar",
            titleEtc = "avatar",
            prodYear = "2009",
            directors = Directors(),
            actors = Actors(),
            nation = "미국",
            company = "Twentieth Century-Fox Film Corporation,Lightstorm",
            plots = Plots(),
            runtime = "162",
            rating = "12세관람가",
            genre = "액션,SF,어드벤처",
            kmdbUrl = "",
            posters = ""
        )
        val movie = Movie(
            title = "아바타",
            posterUrl = "",
            directorName = "정보 없음",
            prodYear = "2009",
            nation = "미국",
            runtimeWithMinutes = "162분",
            plotText = "줄거리 정보가 없습니다.",
            kmdbUrl = ""
        )
        val mockResponse = KmdbResponse(
            query = "",
            kmaQuery = "아바타",
            totalCount = 1,
            data = listOf(
                CollectionData(
                    collName = "kmdb_new2",
                    totalCount = 1,
                    count = 1,
                    result = listOf(movieInfo1)
                )
            )
        )
        `when`(movieDataSource.searchMovies(title = title)).thenReturn(Result.success(mockResponse))

        val result = movieRepository.searchMovies(title)
        val movieList = result.getOrNull()
        assertThat(result.isSuccess, `is`(true))
        assertThat(movieList?.size, `is`(1))
        assertThat(movieList?.get(0), `is`(movie))
        verify(movieDataSource).searchMovies(title = title)
    }

    @Test
    fun `검색 결과가 없을 때 예외 발생`() = runTest {
        val title = "djqtsmsdughk"
        val mockResponse = KmdbResponse(
            query = "",
            kmaQuery = "",
            totalCount = 0,
            data = emptyList()
        )
        `when`(movieDataSource.searchMovies(title = title)).thenReturn(Result.success(mockResponse))

        val result = movieRepository.searchMovies(title)

        assertThat(result.isSuccess, `is`(true))
        assertThat(result.getOrNull(), `is`(emptyList()))
        verify(movieDataSource).searchMovies(title = title)
    }

}
