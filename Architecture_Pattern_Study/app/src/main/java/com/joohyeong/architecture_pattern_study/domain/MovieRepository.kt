package com.joohyeong.architecture_pattern_study.domain

class MovieRepository {
    fun fetchMovies(): Movies {
        return Movies(
            listOf(
                Movie("1", "Inception", "https://joyposter.cafe24.com/NEW-posters/T600X848Poster/TMY-959.jpg", "8.8", "148 min"),
                Movie("2", "The Dark Knight", "https://img1.daumcdn.net/thumb/R720x0/?fname=http://photo-media.daum-img.net/200905/06/khan/20090506230707228.jpeg", "9.0", "152 min"),
                Movie("3", "Interstellar", "https://blog.kakaocdn.net/dn/x7UCP/btqy4dZ2ef5/UjU276GKXbK5KGQxK7cus1/img.jpg", "8.6", "169 min"),
                Movie("4", "Parasite", "https://www.news1.kr/_next/image?url=https%3A%2F%2Fi3n.news1.kr%2Fsystem%2Fphotos%2F2024%2F7%2F16%2F6760884%2Fhigh.jpg&w=1920&q=75", "8.6", "132 min"),
                Movie("5", "The Shawshank Redemption", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRbiqxgtx87YHHhma8MYpyaJkl0Oc3d6q0OSw&s", "9.3", "142 min"),
                Movie("6", "Pulp Fiction", "https://img.khan.co.kr/news/2016/11/24/l_2016112501003520400287685.jpg", "8.9", "154 min")
            )
        )
    }
}
