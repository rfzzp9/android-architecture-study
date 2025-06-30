package com.example.mvc_project.view

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.mvc_project.R
import com.example.mvc_project.databinding.DialogMovieDetailBinding
import com.example.mvc_project.viewmodel.MovieDetailViewModel
import kotlinx.coroutines.launch

class MovieDetailDialogFragment : DialogFragment() {

    private var _binding: DialogMovieDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MovieDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MovieDetailViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDialog()
        setupObservers()
        loadMovieData()
    }

    private fun setupDialog() {
        dialog?.apply {
            setCanceledOnTouchOutside(true)
            window?.apply {
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setFlags(
                    WindowManager.LayoutParams.FLAG_DIM_BEHIND,
                    WindowManager.LayoutParams.FLAG_DIM_BEHIND
                )
                setDimAmount(0.6f)
            }

            setOnShowListener {
                val dialogSize = getDialogSize()
                window?.attributes = window?.attributes?.apply {
                    width = dialogSize.x
                    height = dialogSize.y
                }
            }
        }
    }

    private fun getDialogSize(): Point {
        val windowManager = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displaySize = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val metrics = windowManager.currentWindowMetrics
            Point(metrics.bounds.width(), metrics.bounds.height())
        } else {
            val size = Point()
            @Suppress("DEPRECATION")
            windowManager.defaultDisplay.getSize(size)
            size
        }

        return Point(
            (displaySize.x * 0.9f).toInt(),
            (displaySize.y * 0.8f).toInt()
        )
    }

    private fun loadMovieData() {
        arguments?.getParcelable<MovieUiState>(KEY_MOVIE)?.let { movie ->
            viewModel.setMovieData(movie)
        } ?: run {
            showError("영화 정보를 불러올 수 없습니다.")
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movieDetailUiState.collect { movieDetailState ->
                    displayMovieDetails(movieDetailState)
                }
            }
        }
    }

    // 영화 상세 정보 표시 로직
    private fun displayMovieDetails(movieDetail: MovieDetailUiState) {
        with(binding) {
            setTextOrDefault(tvMovieName, movieDetail.movieName)
            setTextOrDefault(tvMovieRunningTime, movieDetail.movieRunningTime, "분")
            setTextOrDefault(tvMovieGrade, movieDetail.movieGrade)
            setTextOrDefault(tvMovieActors, movieDetail.actorName)
            setTextOrDefault(tvMovieDirector, movieDetail.director)
            setTextOrDefault(tvMoviePlot, movieDetail.plotText)
            setTextOrDefault(tvMovieReleaseDate, movieDetail.prodYear)

            loadMoviePoster(movieDetail.moviePoster)
        }
    }

    // 각 텍스트뷰 설정 로직
    private fun setTextOrDefault(textView: android.widget.TextView, text: String, suffix: String = "") {
        textView.text = if (text.isNotBlank()) {
            "$text$suffix"
        } else {
            getString(R.string.no_information)
        }
    }

    // 포스터 이미지 로드
    private fun loadMoviePoster(posterUrl: String) {
        if (posterUrl.isNotBlank()) {
            Glide.with(requireContext())
                .load(posterUrl)
                .placeholder(R.drawable.placeholder_movie_poster)                     // 로딩 중 이미지
                .error(R.drawable.error_movie_poster)                                 // 에러 시 이미지
                .into(binding.ivMoviePoster)
        } else {
            binding.ivMoviePoster.setImageResource(R.drawable.empty_movie_poster)     //이미지 없을 경우 기본 이미지
        }
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val KEY_MOVIE = "movie_data"

        fun newInstance(movieData: MovieUiState): MovieDetailDialogFragment {
            return MovieDetailDialogFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_MOVIE, movieData)
                }
            }
        }
    }
}