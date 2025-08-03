package com.csb.data.repository

import android.util.Log
import com.csb.data.network.MovieApiService
import com.csb.domain.MovieRepository
import com.csb.domain.ResultMovieClass
import retrofit2.HttpException
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApiService
) : MovieRepository {
    val serviceKey = "7PT3KTQJJJC189RS9XCZ"

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
                    title = movie.title.orEmpty(),

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