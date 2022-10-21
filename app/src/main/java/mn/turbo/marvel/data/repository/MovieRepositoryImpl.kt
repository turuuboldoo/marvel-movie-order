package mn.turbo.marvel.data.repository

import javax.inject.Inject
import mn.turbo.marvel.data.remote.MarvelApi
import mn.turbo.marvel.data.remote.dto.MovieDto
import mn.turbo.marvel.domain.repository.MovieRepository

class MovieRepositoryImpl @Inject constructor(
    private val api: MarvelApi
) : MovieRepository {

    override suspend fun getMovies(): List<MovieDto> = api.getMovies().data

    override suspend fun getMovieById(movieId: Int): MovieDto = api.getMovieById(movieId)
}