package com.example.mvc_yongho.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.example.mvc_yongho.databinding.ActivityMainBinding
import com.example.mvc_yongho.model.data.MovieInfo
import com.example.mvc_yongho.model.repository.MovieRepository
import com.example.mvc_yongho.presenter.MainContract
import com.example.mvc_yongho.presenter.MainPresenter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainContract.View {
    private lateinit var binding: ActivityMainBinding
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var presenter: MainContract.Presenter

    @Inject
    lateinit var movieRepository: MovieRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        presenter = MainPresenter(movieRepository, this)
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
        val query = binding.searchInputEditText.text?.toString()?.trim() ?: ""
        hideKeyboard()
        presenter.searchMovies(title = query)
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.searchInputEditText.windowToken, 0)
    }

    override fun showMovies(movies: List<MovieInfo>) {
        movieAdapter.submitList(movies)
    }

    override fun showError(message: String) {
        movieAdapter.submitList(emptyList())
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

}

