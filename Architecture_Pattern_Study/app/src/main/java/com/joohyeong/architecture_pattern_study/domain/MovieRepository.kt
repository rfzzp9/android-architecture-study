package com.joohyeong.architecture_pattern_study.domain

class MovieRepository {
    fun fetchMovies(): Movies {
        return Movies(
            listOf(
                Movie(1, "Inception", "https://example.com/inception.jpg", "8.8", "148 min"),
                Movie(2, "The Dark Knight", "https://example.com/dark_knight.jpg", "9.0", "152 min"),
                Movie(3, "Interstellar", "https://example.com/interstellar.jpg", "8.6", "169 min"),
                Movie(4, "Parasite", "https://example.com/parasite.jpg", "8.6", "132 min"),
                Movie(5, "The Shawshank Redemption", "https://example.com/shawshank.jpg", "9.3", "142 min"),
                Movie(6, "Pulp Fiction", "https://example.com/pulp_fiction.jpg", "8.9", "154 min")
            )
        )
    }
}
