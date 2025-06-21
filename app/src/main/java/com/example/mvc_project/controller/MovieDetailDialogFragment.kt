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
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.mvc_project.databinding.DialogMovieDetailBinding
import kotlinx.coroutines.launch

class MovieDetailDialogFragment : DialogFragment() {

    private var _binding: DialogMovieDetailBinding? = null
    private val binding get() = _binding!!
    private var movieList: MovieUiState? = null

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
        restoreMovieData()
        initViews()

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

    private fun restoreMovieData() {
        arguments?.let { args ->
            movieList = MovieUiState(
                movieName = args.getString("movie_name"),
                moviePoster = args.getString("movie_poster"),
                movieRunningTime = args.getString("movie_running_time"),
                movieGrade = args.getString("movie_grade"),
                actorName = args.getString("actor_nm"),
                director = args.getString("director"),
                plotText = args.getString("plot_text"),
                prodYear = args.getString("prod_year")
            )
        }
    }

    private fun initViews() = with(binding) {
        lifecycleScope.launch {
            movieList?.let { movie ->
                tvMovieName.text = movie.movieName ?: "정보가 없습니다."
                tvMovieRunningTime.text = "${movie.movieRunningTime}분" ?: "정보가 없습니다."
                tvMovieGrade.text = movie.movieGrade ?: "정보가 없습니다."
                tvMovieActors.text = movie.actorName ?: "정보가 없습니다."
                tvMovieDirector.text = movie.director ?: "정보가 없습니다."
                tvMoviePlot.text = movie.plotText ?: "정보가 없습니다."
                tvMovieReleaseDate.text = movie.prodYear ?: "정보가 없습니다."

                movie.moviePoster?.let { posterUrl ->
                    Glide.with(requireContext())
                        .load(posterUrl)
                        .into(ivMoviePoster)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(movieData: MovieUiState): MovieDetailDialogFragment {
            val fragment = MovieDetailDialogFragment()
            val args = Bundle()
            args.putString("movie_name", movieData.movieName)
            args.putString("movie_poster", movieData.moviePoster)
            args.putString("movie_running_time", movieData.movieRunningTime)
            args.putString("movie_grade", movieData.movieGrade)
            args.putString("actor_nm", movieData.actorName)
            args.putString("director", movieData.director)
            args.putString("plot_text", movieData.plotText)
            args.putString("prod_year", movieData.prodYear)
            fragment.arguments = args
            return fragment
        }
    }
}