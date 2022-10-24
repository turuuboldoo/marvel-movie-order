package mn.turbo.marvel.data.repository

import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import mn.turbo.marvel.data.local.dao.MovieDao
import mn.turbo.marvel.data.remote.MarvelApi
import mn.turbo.marvel.di.DispatcherModule
import mn.turbo.marvel.domain.model.Movie
import mn.turbo.marvel.domain.repository.MovieRepository

class MovieRepositoryImpl @Inject constructor(
    private val api: MarvelApi,
    private val movieDao: MovieDao,
    @DispatcherModule.IoDispatcher val ioDispatcher: CoroutineDispatcher
) : MovieRepository {

    override suspend fun getMovies(): List<Movie> {
        val movies = api.getMovies().data
            .map { it.toMovieEntity() }

        withContext(ioDispatcher) {
            movieDao.insert(movies)
        }

        return movieDao.selectAll().map { it.toMovie() }
    }

    override suspend fun getMovieById(movieId: Int): Movie =
        withContext(ioDispatcher) {
            movieDao.selectById(movieId).toMovie()
        }
}
