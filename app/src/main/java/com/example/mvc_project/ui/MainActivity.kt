package com.example.mvc_project.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.mvc_project.R
import com.example.mvc_project.data.MovieListDataSource
import com.example.mvc_project.data.MovieListRepositoryImpl
import com.example.mvc_project.data.network.ApiService
import com.example.mvc_project.data.network.RetrofitInstance
import com.example.mvc_project.databinding.ActivityMainBinding
import com.example.mvc_project.presentation.MainViewModel
import com.example.mvc_project.presentation.MovieAdapter
import com.example.mvc_project.presentation.MovieUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var movieAdapter: MovieAdapter
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        initObserver()
    }

    private fun initViews() = with(binding) {
        movieAdapter = MovieAdapter { movieItem ->
            onMovieItemClicked(movieItem)
        }

        rvMovieList.apply {
            adapter = movieAdapter
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun showMovieDetail(movie: MovieUiState) {
        val movieDetailDialog = MovieDetailDialogFragment.Companion.newInstance(movie)
        movieDetailDialog.show(
            supportFragmentManager,
            MovieDetailDialogFragment::class.java.simpleName
        )
    }

    private fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movieListStateFlow.collect { movieList ->
                    movieAdapter.submitList(movieList)
                }
            }
        }
    }

    private fun onMovieItemClicked(movie: MovieUiState) {
        showMovieDetail(movie)
    }

}