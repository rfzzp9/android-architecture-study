package com.example.mvc_yongho.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.example.mvc_yongho.databinding.ActivityMainBinding
import com.example.mvc_yongho.model.repository.MovieRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var movieAdapter: MovieAdapter

    @Inject
    lateinit var movieRepository: MovieRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        setupRecyclerView()
        setupSearchInput()
    }

    private fun setupRecyclerView() {
        movieAdapter = MovieAdapter()
        binding.recyclerView.adapter = movieAdapter

        movieAdapter.onKmdbClick = { movie ->
            val intent = Intent(Intent.ACTION_VIEW, movie.kmdbUrl.toUri())
            startActivity(intent)
        }
    }

    private fun setupSearchInput() {
        binding.searchInputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun performSearch() {
        val query = binding.searchInputEditText.text?.toString()?.trim()

        if (query.isNullOrEmpty()) {
            Toast.makeText(this@MainActivity, "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        hideKeyboard()
        searchMovies(query)
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.searchInputEditText.windowToken, 0)
    }

    private fun searchMovies(title: String) {
        lifecycleScope.launch {
            movieRepository.searchMovies(title = title)
                .onSuccess { movies ->
                    movieAdapter.submitList(movies)
                }
                .onFailure { exception ->
                    movieAdapter.submitList(emptyList())
                    Toast.makeText(this@MainActivity, exception.message, Toast.LENGTH_SHORT).show()
                }
        }
    }

}

