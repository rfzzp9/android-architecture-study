package com.example.mvc_project.controller

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvc_project.R
import com.example.mvc_project.controller.mapper.toUiModel
import com.example.mvc_project.databinding.ActivityMainBinding
import com.example.mvc_project.model.dataSource.MovieListRemoteDataSource
import com.example.mvc_project.model.network.ApiService
import com.example.mvc_project.model.network.RetrofitInstance
import com.example.mvc_project.model.repository.MovieListRemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var apiService: ApiService
    private lateinit var movieListRemoteRepository: MovieListRemoteRepository
    private var onMovieListFinishListener: ((MovieUiState) -> Unit)? = null

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
        initObservers()
    }

    private fun initViews() = with(binding) {
        movieAdapter = MovieAdapter(
            onMovieListFinishListener = onMovieListFinishListener
        )

        rvMovieList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = movieAdapter
        }

        rvMovieList.addItemDecoration(
            DividerItemDecoration(
                this@MainActivity,
                DividerItemDecoration.VERTICAL
            )
        )
        movieListRemoteRepository =
            MovieListRemoteRepository(MovieListRemoteDataSource(getApiService()))
    }

    private fun initObservers() {
        lifecycleScope.launch {
            try {
                val movies = movieListRemoteRepository.fetchMovieList()
                val movieUiStateList = movies.data.first().result.map {
                    it.toUiModel()
                }

                withContext(Dispatchers.Main) {
                    movieAdapter.submitList(movieUiStateList)
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "영화 목록을 불러오는데 실패했습니다.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun getApiService(): ApiService {
        apiService = RetrofitInstance.getInstance().create(ApiService::class.java)
        return apiService
    }
}