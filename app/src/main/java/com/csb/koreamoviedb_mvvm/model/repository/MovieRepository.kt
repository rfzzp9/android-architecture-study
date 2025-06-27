package com.csb.koreamoviedb_mvvm.model.repository

import android.util.Log
import com.csb.koreamoviedb_mvvm.BuildConfig
import com.csb.koreamoviedb_mvvm.model.data.ResultMovieClass
import com.csb.koreamoviedb_mvvm.model.network.ApiClient
import com.csb.koreamoviedb_mvvm.tools.removeHighlightTags
import retrofit2.HttpException
import javax.inject.Inject

class MovieRepository @Inject constructor() {

    val serviceKey = BuildConfig.SERVICE_KEY


    suspend fun getMovies(
        query: String? = null,
        title: String? = null,
        actor: String? = null,
        director: String? = null
    ): List<ResultMovieClass> {
        try {

            val response = ApiClient.movieApiService.searchMovie(
                query = query,
                title = title,
                actor = actor,
                director = director,
                serviceKey = serviceKey
            )


            return response.data?.firstOrNull()?.result?.map { movie ->
                ResultMovieClass(
                    title = removeHighlightTags(movie.title.orEmpty()).toString(),

                    directors = movie.directors?.directors
                        ?.mapNotNull { it.directorNm?.trim() }
                        ?: emptyList(),

                    actors = movie.actors?.actors
                        ?.mapNotNull { it.actorNm?.trim() }
                        ?: emptyList(),

                    plots = movie.plots?.plot
                        ?.mapNotNull { it.plotText?.trim() }
                        ?: emptyList(),

                    detailInfo = movie.kmdbUrl.orEmpty(),
                    posters = movie.posters.orEmpty(),
                    runtime = movie.runtime.orEmpty(),
                    ratingGrade = movie.rating.orEmpty(),
                    releaseDate = movie.repRatDate.orEmpty()
                )
            } ?: emptyList()

        } catch (e: HttpException) {
            Log.e("MovieRepository", "HTTP 에러?: ${e.code()} ${e.message()}", e)
            throw e
        } catch (e: Exception) {
            Log.e("MovieRepository", "에러", e)
            throw e
        }
    }
}
