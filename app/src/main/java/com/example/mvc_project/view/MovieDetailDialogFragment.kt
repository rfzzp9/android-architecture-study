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
                clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                setDimAmount(0.6f)
            }
        }

        dialog?.setOnShowListener {
            resizeDialog()
        }
    }

    private fun resizeDialog() {
        val displaySize = getAvailableDisplaySize(requireContext())
        val targetWidth = (displaySize.x * 0.9f).toInt()
        val targetHeight = (displaySize.y * 0.8f).toInt()

        dialog?.window?.attributes?.apply {
            width = targetWidth
            height = targetHeight
            dialog?.window?.attributes = this
        }
    }

    private fun getAvailableDisplaySize(context: Context): Point {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getDisplaySizeForApiR(context)
        } else {
            getDisplaySizeForLegacy(context)
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun getDisplaySizeForApiR(context: Context): Point {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val metrics = windowManager.currentWindowMetrics
        val windowInsets = metrics.windowInsets
        val insets: android.graphics.Insets = windowInsets.getInsetsIgnoringVisibility(
            WindowInsets.Type.navigationBars() or WindowInsets.Type.displayCutout()
        )

        val width = metrics.bounds.width() - insets.left - insets.right
        val height = metrics.bounds.height() - insets.top - insets.bottom
        return Point(width, height)
    }

    private fun getDisplaySizeForLegacy(context: Context): Point {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size
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

    private fun displayMovieDetails(movieDetail: MovieDetailUiState) {
        with(binding) {
            // 영화 기본 정보
            tvMovieName.text = if (movieDetail.movieName.isNotBlank()) {
                movieDetail.movieName
            } else {
                getString(R.string.no_information)
            }

            tvMovieRunningTime.text = if (movieDetail.movieRunningTime.isNotBlank()) {
                "${movieDetail.movieRunningTime}분"
            } else {
                getString(R.string.no_information)
            }

            tvMovieGrade.text = if (movieDetail.movieGrade.isNotBlank()) {
                movieDetail.movieGrade
            } else {
                getString(R.string.no_information)
            }

            tvMovieActors.text = if (movieDetail.actorName.isNotBlank()) {
                movieDetail.actorName
            } else {
                getString(R.string.no_information)
            }

            tvMovieDirector.text = if (movieDetail.director.isNotBlank()) {
                movieDetail.director
            } else {
                getString(R.string.no_information)
            }

            tvMoviePlot.text = if (movieDetail.plotText.isNotBlank()) {
                movieDetail.plotText
            } else {
                getString(R.string.no_information)
            }

            tvMovieReleaseDate.text = if (movieDetail.prodYear.isNotBlank()) {
                movieDetail.prodYear
            } else {
                getString(R.string.no_information)
            }

            loadMoviePoster(movieDetail.moviePoster)
        }
    }

    // 포스터 이미지 로드
    private fun loadMoviePoster(posterUrl: String) {
        if (posterUrl.isNotBlank()) {
            Glide.with(requireContext())
                .load(posterUrl)
                .placeholder(android.R.drawable.ic_menu_gallery)                     // 로딩 중 이미지
                .error(android.R.drawable.ic_menu_report_image)                      // 에러 시 이미지
                .into(binding.ivMoviePoster)
        } else {
            binding.ivMoviePoster.setImageResource(R.drawable.empty_movie_poster)    //이미지 없을 경우 기본 이미지
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