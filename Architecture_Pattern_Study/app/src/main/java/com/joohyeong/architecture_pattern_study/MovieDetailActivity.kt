package com.joohyeong.architecture_pattern_study

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.joohyeong.architecture_pattern_study.databinding.ActivityMovieDetailBinding

class MovieDetailActivity : AppCompatActivity() {
    private val binding = ActivityMovieDetailBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}