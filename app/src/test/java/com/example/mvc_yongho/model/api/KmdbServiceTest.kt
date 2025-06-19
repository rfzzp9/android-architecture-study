package com.example.mvc_yongho.model.api

import com.example.mvc_yongho.model.network.api.KmdbService
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit.*
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.nio.charset.StandardCharsets

class KmdbServiceTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var kmdbService: KmdbService
    private val json = Json { ignoreUnknownKeys = true }

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        kmdbService = Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(KmdbService::class.java)
    }

    private fun enqueueResponse(filename: String) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream("api-response/$filename")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockWebServer.enqueue(mockResponse.setBody(source.readString(StandardCharsets.UTF_8)))
    }

    @Test
    fun 영화_정보_요청() = runTest {
        enqueueResponse(filename = "/KmdbResponse.json")
        val response = kmdbService.searchMovies(title = "아바타")
        val data = requireNotNull(response.data)
        assertThat(response.totalCount, `is`(6))
        assertThat(data[0].result[0].title, `is`("  !HS 아바타 !HE "))
    }

    @After
    fun after() {
        mockWebServer.shutdown()
    }


}
