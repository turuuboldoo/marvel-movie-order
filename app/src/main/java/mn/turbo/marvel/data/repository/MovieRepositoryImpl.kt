package mn.turbo.marvel.data.repository

import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import mn.turbo.marvel.data.local.dao.MovieDao
import mn.turbo.marvel.data.remote.MarvelApi
import mn.turbo.marvel.di.IoDispatcher
import mn.turbo.marvel.domain.model.Movie
import mn.turbo.marvel.domain.repository.MovieRepository

class MovieRepositoryImpl @Inject constructor(
    private val api: MarvelApi,
    private val movieDao: MovieDao,
    @IoDispatcher val ioDispatcher: CoroutineDispatcher
) : MovieRepository {

    override suspend fun getMovies(): List<Movie> {
        val movies = movieDao.selectAll().map { it.toMovie() }

        return movies.ifEmpty {
            val movieFromRemote = api.getMovies().data
            withContext(ioDispatcher) {
                movieDao.insert(
                    movieEntity = movieFromRemote.map { it.toMovieEntity() }
                )
            }
            movieFromRemote.map { it.toMovie() }
        }
    }

    override suspend fun getMovieById(movieId: Int): Movie =
        withContext(ioDispatcher) {
            api.getMovieById(movieId).toMovie()
//            movieDao.selectById(movieId).toMovie()
        }
}
