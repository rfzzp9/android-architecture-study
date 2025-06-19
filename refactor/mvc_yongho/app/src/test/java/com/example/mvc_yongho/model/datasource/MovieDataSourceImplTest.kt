package com.example.mvc_yongho.model.datasource

import com.example.mvc_yongho.model.data.KmdbResponse
import com.example.mvc_yongho.model.network.api.KmdbService
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.mockito.Mockito.`when`
import org.mockito.kotlin.verify

class MovieDataSourceImplTest {
    private val kmdbService: KmdbService = mock()
    private lateinit var movieDataSource: MovieDataSourceImpl

    @Before
    fun setUp() {
        movieDataSource = MovieDataSourceImpl(kmdbService)
    }

    @Test
    fun 영화_검색_성공시_Result_Success_반환() = runTest {
        val title = "아바타"
        val mockResponse = KmdbResponse(
            query = "",
            kmaQuery = "아바타",
            totalCount = 6,
            data = emptyList()
        )

        `when`(kmdbService.searchMovies(title = title)).thenReturn(mockResponse)
        val result = movieDataSource.searchMovies(title= title)

        assertThat(result.isSuccess, `is`(true))
        assertThat(result.getOrNull(), `is`(mockResponse))
        verify(kmdbService).searchMovies(title = title)
    }

}
