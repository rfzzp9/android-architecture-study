package com.joohyeong.architecture_pattern_study.presentation.moviedetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.joohyeong.architecture_pattern_study.R
import com.joohyeong.architecture_pattern_study.databinding.ActivityMovieDetailBinding
import com.joohyeong.architecture_pattern_study.domain.MovieDetail
import kotlinx.coroutines.launch

class MovieDetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMovieDetailBinding.inflate(layoutInflater) }
    private val viewModel: MovieDetailViewModel by viewModels()

    private val movieId: String by lazy {
        intent.getStringExtra(EXTRA_MOVIE_ID)
            ?: throw IllegalArgumentException("Movie ID is required")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        applySystemBarsPadding()

        initObserve()
        viewModel.loadMovieDetail(movieId)
    }

    private fun initObserve() {
        lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                when (uiState) {
                    is MovieDetailUIState.Success -> showMovieDetail(uiState.movieDetail)
                    MovieDetailUIState.Loading -> Unit
                    is MovieDetailUIState.Error -> showLoadMovieDetailError()
                }
            }
        }
    }

    private fun showMovieDetail(movieDetail: MovieDetail) {
        bindMovieDetail(movieDetail)
    }

    private fun showLoadMovieDetailError() {
        Toast.makeText(
            this,
            getString(R.string.error_message_load_movie_detail),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun bindMovieDetail(movieDetail: MovieDetail) {
        with(binding) {
            Glide.with(this@MovieDetailActivity)
                .load(movieDetail.posterUrl)
                .apply(RequestOptions.centerCropTransform()).fallback(R.drawable.image_error)
                .error(R.drawable.image_error)
                .into(imageMoviePoster)
            textMovieTitle.text = movieDetail.title
            textMovieDirector.text = getString(
                R.string.text_movie_detail_director_prefix,
                movieDetail.directors.joinToString(", ")
            )
            textMovieActors.text = getString(
                R.string.text_movie_detail_actor_prefix,
                movieDetail.actors.joinToString(", ")
            )
            textMovieSynopsis.text =
                getString(R.string.text_movie_detail_synopsis_prefix, movieDetail.plot)
            textMovieRuntime.text =
                getString(R.string.text_movie_detail_runtime_prefix, movieDetail.runtime)
            textMovieRating.text =
                getString(R.string.text_movie_detail_rating_prefix, movieDetail.rating)
            textMovieReleaseDate.text =
                getString(R.string.text_movie_detail_release_date_prefix, movieDetail.releaseDate)
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
