package com.csb.koreamoviedb_mvvm.data.repository

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import com.csb.koreamoviedb_mvvm.BuildConfig
import com.csb.koreamoviedb_mvvm.data.network.MovieApiService
import com.csb.koreamoviedb_mvvm.domain.ResultMovieClass
import com.csb.koreamoviedb_mvvm.repository.MovieRepository
import com.csb.koreamoviedb_mvvm.presentation.search.component.removeHighlightTags
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApiService
) : MovieRepository {
    val serviceKey = BuildConfig.SERVICE_KEY


    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getMovies(
        query: String?,
        title: String?,
        actor: String?,
        director: String?
    ): List<ResultMovieClass> {
        try {

            val response = api.searchMovie(
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
            Log.e("MovieRepository", "HTTP 에러?:  ${e}")
            throw e
        } catch (e: Exception) {
            Log.e("MovieRepository", "에러", e)
            throw e
        }

    }

}