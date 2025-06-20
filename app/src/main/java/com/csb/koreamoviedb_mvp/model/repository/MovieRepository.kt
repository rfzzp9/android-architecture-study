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

    // Retrofit 설정: KMDb API를 XML로 파싱할 수 있도록 설정
    private val retrofit = Retrofit.Builder()
        .baseUrl(context.getString(R.string.baseUrl))
        .addConverterFactory(SimpleXmlConverterFactory.create()) // XML 응답을 파싱하기 위한 컨버터
        .build()

    // Retrofit 인터페이스 구현체 생성
    private val service = retrofit.create(KmdbAPI::class.java)

    // 실제 API 호출 함수 (동기 호출 방식)
    fun searchMovies(keyword: String, filter: Filter): List<ResultMovieClass> {
        // 필터 값에 따라 쿼리 파라미터 설정
        val call = when (filter) {
            Filter.FILTER_ACTOR -> service.searchMovies(
                serviceKey = context.getString(R.string.API_KEY),
                actor = keyword
            )
            Filter.FILTER_DIRECTOR -> service.searchMovies(
                serviceKey = context.getString(R.string.API_KEY),
                director = keyword
            )
            Filter.FILTER_TITLE -> service.searchMovies(
                serviceKey = context.getString(R.string.API_KEY),
                title = keyword
            )
            Filter.FILTER_ALL -> service.searchMovies(
                serviceKey = context.getString(R.string.API_KEY),
                query = keyword
            )
        }

        // 동기 방식으로 API 호출
        val response = call.execute()

        // 실패한 경우 예외 발생
        if (!response.isSuccessful)
            throw Exception("호출 실패라고?: ${response.code()}")

        // 결과 파싱 및 가공
        val result = mutableListOf<ResultMovieClass>()

        // 디버깅용 로그 출력
        Log.d("rowsSize", response.body()?.result?.rows?.size.toString())

        // XML 응답 데이터 → 우리가 사용할 데이터 형태로 가공
        response.body()?.result?.rows?.forEach { row ->
            Log.d("response.body()", "있음")

            val directors = row.directors?.directors
                ?.map { RemoveHighlightTags(it.directorNm ?: "") }
                ?: emptyList()

            val actors = row.actors?.actors
                ?.map { RemoveHighlightTags(it.actorNm ?: "") }
                ?: emptyList()

            val plots = row.plots?.plot
                ?.map { RemoveHighlightTags(it.plotText ?: "") }
                ?: emptyList()

            // 각 항목을 ResultMovieClass 객체로 변환 후 리스트에 추가
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
                    releaseDate = row.repRatDate ?: ""
                )
            )
        }
        //반홤
        return result
    }
}
