package com.joohyeong.architecture_pattern_study.presentation.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.joohyeong.architecture_pattern_study.databinding.ActivityMainBinding
import com.joohyeong.architecture_pattern_study.domain.Movies
import com.joohyeong.architecture_pattern_study.presentation.moviedetail.MovieDetailActivity

class MainActivity : AppCompatActivity(), MainContract.View {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val presenter by lazy {
        MainPresenter(view = this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        applySystemBarsPadding()

        fetchMovies()
    }

    private fun fetchMovies() {
        presenter.loadMovies()
    }

    private fun applySystemBarsPadding() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun showMovies(movies: Movies) {
        binding.recyclerMovie.adapter = MovieAdapter(
            movies = movies,
            onMovieClick = {
                startActivity(MovieDetailActivity.newIntent(context = this, movieId = it))
            }
        )
    }
}
