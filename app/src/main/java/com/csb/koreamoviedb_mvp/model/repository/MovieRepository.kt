package com.csb.koreamoviedb_mvp.model.repository

import android.content.Context
import android.util.Log
import com.csb.koreamoviedb_mvp.R
import com.csb.koreamoviedb_mvp.model.api.KmdbAPI
import com.csb.koreamoviedb_mvp.model.data.ResultMovieClass
import com.csb.koreamoviedb_mvp.tools.Filter
import com.csb.koreamoviedb_mvp.tools.RemoveHighlightTags
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class MovieRepository(private val context: Context) {

    //api 호출변수
    private val retrofit = Retrofit.Builder()
        .baseUrl(context.getString(R.string.baseUrl))
        .addConverterFactory(SimpleXmlConverterFactory.create())
        .build()

    private val service = retrofit.create(KmdbAPI::class.java)

    fun searchMovies(keyword: String, filter: Filter): List<ResultMovieClass> {
        val call = when (filter) {
            Filter.FILTER_ACTOR -> service.searchMovies(
                serviceKey = context.getString(R.string.API_KEY), actor = keyword
            )

            Filter.FILTER_DIRECTOR -> service.searchMovies(
                serviceKey = context.getString(R.string.API_KEY), director = keyword
            )

            Filter.FILTER_TITLE -> service.searchMovies(
                serviceKey = context.getString(R.string.API_KEY), title = keyword
            )

            Filter.FILTER_ALL -> service.searchMovies(
                serviceKey = context.getString(R.string.API_KEY), query = keyword
            )
        }

        val response = call.execute()
        if (!response.isSuccessful)
            throw Exception("호출 실패라고?: ${response.code()}")

        val result = mutableListOf<ResultMovieClass>()

        Log.d("rowsSize", response.body()?.result?.rows?.size.toString())
        response.body()?.result?.rows?.forEach { row ->
            Log.d("response.body()", "있음")
            val directors =
                row.directors?.directors?.map { RemoveHighlightTags(it.directorNm ?: "") }
                    ?: emptyList()
            val actors =
                row.actors?.actors?.map { RemoveHighlightTags(it.actorNm ?: "") } ?: emptyList()
            val plots =
                row.plots?.plot?.map { RemoveHighlightTags(it.plotText ?: "") } ?: emptyList()

            result.add(
                ResultMovieClass(
                    title = RemoveHighlightTags(row.title ?: ""),
                    detailInfo = row.kmdbUrl ?: "",
                    directors = directors,
                    actors = actors,
                    plots = plots,
                    posters = row.posters ?: "",
                    runtime = row.runtime ?: "",
                    ratingGrade = row.rating ?: "",
                    releaseDate = row.repRatDate ?: "",
                )
            )
        }

        return result
    }
}