package com.example.mvc_project.presentation.ui.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.mvc_project.R
import com.example.mvc_project.databinding.ActivityMainBinding
import com.example.mvc_project.presentation.ui.detail.MovieDetailDialogFragment
import com.example.mvc_project.presentation.ui.model.MovieUiState
import com.example.mvc_project.presentation.sideeffect.MainSideEffect
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var viewModel: MainViewModel

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

        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory.create()
        )[MainViewModel::class.java]

        initViews()
        observeViewState()
        observeSideEffects()
    }

    private fun initViews() = with(binding) {
        movieAdapter = MovieAdapter { movieItem ->
            viewModel.processIntent(MainIntent.ShowMovieDetail(movieItem))
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

        BtnReload.setOnClickListener {
            viewModel.processIntent(MainIntent.RetryLoadMovieList)
        }
    }

    private fun observeViewState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { state ->
                    render(state)
                }
            }
        }
    }

    private fun observeSideEffects() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sideEffect.collect { sideEffect ->
                    handleSideEffect(sideEffect)
                }
            }
        }
    }

    private fun render(state: MainViewState) {
        binding.apply {
            progressBar.isVisible = state.isLoading            // 로딩

            val hasError = state.error != null                 // 에러 상태
            ivError.isVisible = hasError
            tvError.isVisible = hasError
            BtnReload.isVisible = hasError

            rvMovieList.isVisible = state.movies.isNotEmpty() && !state.isLoading && !hasError          // 영화 목록 업데이트
            if (state.movies.isNotEmpty()) {
                movieAdapter.submitList(state.movies)
            }
        }
    }

    private fun handleSideEffect(sideEffect: MainSideEffect) {
        when (sideEffect) {
            is MainSideEffect.ShowMovieDetailDialog -> {
                showMovieDetailDialog(sideEffect.movie)
            }
            is MainSideEffect.ShowErrorMessage -> {
                showErrorSnackbar(sideEffect.message)
            }
        }
    }

    private fun showMovieDetailDialog(movie: MovieUiState) {
        val movieDetailDialog = MovieDetailDialogFragment.newInstance(movie)
        movieDetailDialog.show(
            supportFragmentManager,
            MovieDetailDialogFragment::class.java.simpleName
        )
    }

    private fun showErrorSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setAction("재시도") {
                viewModel.processIntent(MainIntent.RetryLoadMovieList)
            }
            .show()
    }
}