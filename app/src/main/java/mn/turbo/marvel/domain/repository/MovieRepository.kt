package mn.turbo.marvel.domain.repository

import mn.turbo.marvel.domain.model.Movie

interface MovieRepository {
    suspend fun getMovies(): List<Movie>
    suspend fun getMovieById(movieId: Int): Movie
}
