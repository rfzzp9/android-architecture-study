package com.example.mvp_project.presentation.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvc_project.R
import com.example.mvc_project.databinding.ActivityMainBinding
import com.example.mvc_project.model.usecase.GetMovieListUseCase
import com.example.mvc_project.model.dataSource.MovieListRemoteDataSource
import com.example.mvc_project.model.network.ApiService
import com.example.mvc_project.model.network.RetrofitInstance
import com.example.mvc_project.model.repository.MovieListRemoteRepository
import com.example.mvc_project.presenter.main.MainContract
import com.example.mvc_project.presenter.main.MainPresenter
import com.example.mvc_project.presenter.model.MovieUiState
import com.example.mvc_project.view.MovieAdapter
import com.example.mvc_project.view.MovieDetailDialogFragment

class MainActivity : AppCompatActivity(), MainContract.View {

    private lateinit var binding: ActivityMainBinding
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var presenter: MainContract.Presenter

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
        setupPresenter()
        initViews()
        presenter.loadMovieList()
    }

    private fun setupPresenter() {
        val apiService = RetrofitInstance.getInstance().create(ApiService::class.java)
        val dataSource = MovieListRemoteDataSource(apiService)
        val repository = MovieListRemoteRepository(dataSource)
        val useCase = GetMovieListUseCase(repository)
        presenter = MainPresenter(useCase)
        presenter.attachView(this)
    }

    private fun initViews() = with(binding) {
        movieAdapter = MovieAdapter { movieItem ->
            presenter.onMovieItemClicked(movieItem)
        }

        rvMovieList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = movieAdapter
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    override fun showMovieList(movies: List<MovieUiState>) {
        movieAdapter.submitList(movies)
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showMovieDetail(movie: MovieUiState) {
        val movieDetailDialogFragment = MovieDetailDialogFragment.newInstance(movie)
        movieDetailDialogFragment.show(supportFragmentManager, MovieDetailDialogFragment::class.java.name)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}