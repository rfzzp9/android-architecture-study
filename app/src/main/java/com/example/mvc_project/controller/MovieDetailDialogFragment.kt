package com.example.mvc_project.controller

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.graphics.Insets
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.mvc_project.R
import com.example.mvc_project.controller.mapper.toMovieDetailModel
import com.example.mvc_project.databinding.DialogMovieDetailBinding
import kotlinx.coroutines.launch

class MovieDetailDialogFragment : DialogFragment() {

    private var _binding: DialogMovieDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogMovieDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setCanceledOnTouchOutside(false)
        resizeDialogFragment()
        loadMovieData()

    }



    private fun resizeDialogFragment() {
        dialog?.window?.setBackgroundDrawable(android.graphics.Color.TRANSPARENT.toDrawable())
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog?.window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog?.window?.setDimAmount(0.6f)
        dialog!!.setOnShowListener { dialog: DialogInterface? ->
            val availableDisplaySize: Point =
                getAvailableDisplaySize(requireContext())
            val targetWidth = (availableDisplaySize.x * .8f).toInt()
            val targetHeight = (availableDisplaySize.y * .7f).toInt()
            val params = getDialog()!!.window!!.attributes
            params.width = targetWidth
            params.height = targetHeight
            getDialog()!!.window!!.attributes = params
        }
    }

    private fun getAvailableDisplaySize(context: Context): Point {
        if (context is Activity && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val metrics = context.windowManager.currentWindowMetrics
            val windowInsets = metrics.windowInsets
            val insets: Insets = windowInsets.getInsetsIgnoringVisibility(
                WindowInsets.Type.navigationBars()
                        or WindowInsets.Type.displayCutout()
            )
            val width: Int = metrics.bounds.width() - insets.right + insets.left
            val height: Int = metrics.bounds.height() - insets.top + insets.bottom
            return Point(width, height)
        } else {
            val display =
                (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
            val size = Point()
            display.getSize(size)
            return size
        }
    }

    private fun loadMovieData() {
        arguments?.getParcelable<MovieDetailUiState>(KEY_MOVIE)?.let { movie ->
            displayMovieDetails(movie)
        } ?: run {
            showError(getString(R.string.no_data_message))
        }
    }

    private fun displayMovieDetails(movieDetailUiState: MovieDetailUiState) = with(binding) {
        lifecycleScope.launch {
            movieDetailUiState.let { movie ->
                tvMovieName.text = if(movie.movieName.isNotBlank()) {
                    movie.movieName
                } else {
                    getString(R.string.no_movies_message)
                }
                tvMovieRunningTime.text = if(movie.movieRunningTime.isNotBlank()) {
                    movie.movieRunningTime
                } else {
                    getString(R.string.no_movies_message)
                }
                tvMovieGrade.text = if(movie.movieGrade.isNotBlank()) {
                    movie.movieGrade
                } else {
                    getString(R.string.no_movies_message)
                }

                tvMovieActors.text = if (movie.actorName.isNotBlank()) {
                    movie.actorName
                } else {
                    getString(R.string.no_movies_message)
                }

                tvMovieDirector.text = if (movie.director.isNotBlank()) {
                    movie.director
                } else {
                    getString(R.string.no_movies_message)
                }

                tvMoviePlot.text = if (movie.plotText.isNotBlank()) {
                    movie.plotText
                } else {
                    getString(R.string.no_movies_message)
                }

                tvMovieReleaseDate.text = if (movie.prodYear.isNotBlank()) {
                    movie.prodYear
                } else {
                    getString(R.string.no_movies_message)
                }
                loadMoviePoster(movie.moviePoster)
            }

        }
    }

    private fun loadMoviePoster(posterUrl: String) {
        if (posterUrl.isNotBlank()) {
            Glide.with(requireContext())
                .load(posterUrl)
                .placeholder(R.drawable.loading)                     // 로딩 중 이미지
                .error(R.drawable.warning)                      // 에러 시 이미지
                .into(binding.ivMoviePoster)
        } else {
            binding.ivMoviePoster.setImageResource(R.drawable.warning)    //이미지 없을 경우 기본 이미지
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
                    putParcelable(KEY_MOVIE, movieData.toMovieDetailModel())
                }
            }
        }
    }
}