package com.joohyeong.architecture_pattern_study

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.joohyeong.architecture_pattern_study.databinding.ActivityMainBinding
import com.joohyeong.architecture_pattern_study.domain.MovieRepository
import com.joohyeong.architecture_pattern_study.domain.Movies

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val movieRepository = MovieRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        applySystemBarsPadding()

        val movies = movieRepository.fetchMovies()
        initMovieAdapter(movies)
    }

    private fun applySystemBarsPadding() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initMovieAdapter(movies: Movies) {
        binding.recyclerMovie.adapter = MovieAdapter(
            movies = movies,
            onMovieClick = {
                startActivity(MovieDetailActivity.newIntent(context = this, movieId = it.toLong()))
            }
        )
    }
}
