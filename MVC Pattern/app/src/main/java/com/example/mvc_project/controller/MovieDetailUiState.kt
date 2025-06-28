package com.example.mvc_project.controller

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieDetailUiState(
    val moviePoster: String = "",
    val movieName: String = "",
    val movieRunningTime: String = "",
    val movieGrade: String = "",
    val actorName: String = "",
    val director: String = "",
    val plotText: String = "",
    val prodYear: String = "",
) : Parcelable
