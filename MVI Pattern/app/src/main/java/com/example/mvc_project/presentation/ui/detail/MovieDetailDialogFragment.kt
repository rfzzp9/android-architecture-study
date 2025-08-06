package com.example.mvc_project.presentation.ui.detail

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.mvc_project.R
import com.example.mvc_project.databinding.DialogMovieDetailBinding
import com.example.mvc_project.presentation.ui.model.MovieDetailUiState
import com.example.mvc_project.presentation.ui.model.MovieUiState
import com.example.mvc_project.presentation.sideeffect.MovieDetailSideEffect
import kotlinx.coroutines.launch

class MovieDetailDialogFragment : DialogFragment() {

    private var _binding: DialogMovieDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MovieDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            MovieDetailViewModelFactory.create()
        )[MovieDetailViewModel::class.java]
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
        observeViewState()
        observeSideEffects()
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
            viewModel.processIntent(MovieDetailIntent.LoadMovieDetail(movie))
        } ?: run {
            viewModel.processIntent(MovieDetailIntent.DismissDialog)
        }
    }

    private fun observeViewState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { state ->
                    render(state)
                }
            }
        }
    }

    private fun observeSideEffects() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sideEffect.collect { sideEffect ->
                    handleSideEffect(sideEffect)
                }
            }
        }
    }

    private fun render(state: MovieDetailViewState) {
        binding.apply {
            progressBar.isVisible = state.isLoading                                // 로딩 상태
            contentLayout.isVisible = !state.isLoading && state.error == null

            if (!state.isLoading && state.error == null) {                         // 영화 상세정보
                displayMovieDetails(state.movieDetail)
            }
        }
    }

    private fun handleSideEffect(sideEffect: MovieDetailSideEffect) {
        when (sideEffect) {
            is MovieDetailSideEffect.DismissDialog -> {
                dismiss()
            }
            is MovieDetailSideEffect.ShowErrorAndDismiss -> {
                showErrorToast(sideEffect.message)
                dismiss()
            }
        }
    }

    private fun displayMovieDetails(movieDetail: MovieDetailUiState) {
        with(binding) {
            setTextOrDefault(tvMovieName, movieDetail.movieName)
            setTextWithLabel(tvMovieActors, getString(R.string.label_cast), movieDetail.actorName)
            setTextWithLabel(tvMovieDirector, getString(R.string.label_director), movieDetail.director)
            setTextWithLabel(tvMoviePlot, getString(R.string.label_plot), movieDetail.plotText)
            setTextWithLabel(tvMovieRunningTime, getString(R.string.label_runtime), movieDetail.movieRunningTime, "분")
            setTextWithLabel(tvMovieGrade, getString(R.string.label_grade), movieDetail.movieGrade)
            setTextWithLabel(tvMovieReleaseDate, getString(R.string.label_release_date), movieDetail.prodYear)

            loadMoviePoster(movieDetail.moviePoster)
        }
    }

    private fun setTextOrDefault(textView: TextView, text: String) {
        textView.text = if (text.isNotBlank()) {
            text
        } else {
            getString(R.string.no_information)
        }
    }

    private fun setTextWithLabel(textView: TextView, label: String, text: String, suffix: String = "") {
        textView.text = if (text.isNotBlank()) {
            "$label: $text$suffix"
        } else {
            "$label: ${getString(R.string.no_information)}"
        }
    }

    private fun loadMoviePoster(posterUrl: String) {
        if (posterUrl.isNotBlank()) {
            Glide.with(requireContext())
                .load(posterUrl)
                .placeholder(R.drawable.placeholder_movie_poster)
                .error(R.drawable.error_movie_poster)
                .into(binding.ivMoviePoster)
        } else {
            binding.ivMoviePoster.setImageResource(R.drawable.empty_movie_poster)
        }
    }

    private fun showErrorToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
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