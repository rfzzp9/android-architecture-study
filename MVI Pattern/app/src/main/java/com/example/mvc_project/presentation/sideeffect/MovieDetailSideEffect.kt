package com.example.mvc_project.presentation.sideeffect

sealed class MovieDetailSideEffect {
    object DismissDialog : MovieDetailSideEffect()
    data class ShowErrorAndDismiss(val message: String) : MovieDetailSideEffect()
}