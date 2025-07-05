package com.example.mvc_project.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieUiState(
    val movieId: String? = "",
    val movieName: String? = "",
    val moviePoster: String? = "",
    val movieRunningTime: String? = "",
    val movieGrade: String? = "",
    val actorName: String? = "",
    val director: String? = "",
    val plotText: String? = "",
    val prodYear: String? = "",
) : Parcelable