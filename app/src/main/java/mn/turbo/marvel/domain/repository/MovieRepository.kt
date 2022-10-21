package mn.turbo.marvel.domain.repository

import mn.turbo.marvel.data.remote.dto.MovieDto

interface MovieRepository {
    suspend fun getMovies(): List<MovieDto>
    suspend fun getMovieById(movieId: Int): MovieDto
}