package com.joohyeong.architecture_pattern_study.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.joohyeong.architecture_pattern_study.databinding.ActivityMovieDetailBinding
import com.joohyeong.architecture_pattern_study.domain.MovieDetail
import com.joohyeong.architecture_pattern_study.domain.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMovieDetailBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        applySystemBarsPadding()

        val movieId = intent.getStringExtra(EXTRA_MOVIE_ID)
            ?: throw IllegalArgumentException("Movie ID is required")
        CoroutineScope(Dispatchers.Main).launch {
            val movieDetail = MovieRepository.fetchMovieDetailById(movieId)
            bindMovieDetail(movieDetail)
        }
    }

    private fun bindMovieDetail(movieDetail: MovieDetail?) {
        with(binding) {
            Glide.with(this@MovieDetailActivity)
                .load(movieDetail?.posterUrl)
                .apply(RequestOptions.centerCropTransform())
                .into(imageMoviePoster)
            textMovieTitle.text = movieDetail?.title
            textMovieDirector.text = "감독: ${movieDetail?.directors?.joinToString(", ")}"
            textMovieActors.text = "배우: ${movieDetail?.actors?.joinToString(", ")}"
            textMovieSynopsis.text = "줄거리: ${movieDetail?.plot}"
            textMovieRuntime.text = "상영 시간: ${movieDetail?.runtime}분"
            textMovieRating.text = "관람 등급: ${movieDetail?.rating}"
            textMovieReleaseDate.text = "개봉일: ${movieDetail?.releaseDate}"
        }
    }

    private fun applySystemBarsPadding() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.scrollViewMovieDetail) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    companion object {
        private const val EXTRA_MOVIE_ID = "extra_movie_id"

        fun newIntent(context: Context, movieId: String): Intent =
            Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(EXTRA_MOVIE_ID, movieId)
            }
    }
}