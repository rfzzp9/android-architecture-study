package com.joohyeong.architecture_pattern_study.presentation.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.joohyeong.architecture_pattern_study.R
import com.joohyeong.architecture_pattern_study.databinding.ActivityMainBinding
import com.joohyeong.architecture_pattern_study.domain.Movie
import com.joohyeong.architecture_pattern_study.presentation.moviedetail.MovieDetailActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        applySystemBarsPadding()

        initObserve()
        viewModel.processIntent(MainIntent.LoadMovies)
    }

    private fun initObserve() {
        lifecycleScope.launch {
            viewModel.uiState.collect {
                when (it) {
                    is MainUIState.Success -> showMovies(it.movies)
                    MainUIState.Loading -> Unit
                    is MainUIState.Error -> showLoadMoviesError()
                }
            }
        }
    }

    private fun applySystemBarsPadding() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun showMovies(movies: List<Movie>) {
        binding.recyclerMovie.adapter = MovieAdapter(
            movies = movies,
            onMovieClick = {
                startActivity(MovieDetailActivity.newIntent(context = this, movieId = it))
            }
        )
    }

    private fun showLoadMoviesError() {
        Toast.makeText(
            this,
            getString(R.string.error_message_load_movies),
            Toast.LENGTH_SHORT
        ).show()
    }
}
