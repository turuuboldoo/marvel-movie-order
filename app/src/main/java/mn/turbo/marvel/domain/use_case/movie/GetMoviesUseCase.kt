package mn.turbo.marvel.domain.use_case.movie

import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import mn.turbo.marvel.common.Resource
import mn.turbo.marvel.data.local.MovieDao
import mn.turbo.marvel.di.DispatcherModule
import mn.turbo.marvel.domain.model.Movie
import mn.turbo.marvel.domain.repository.MovieRepository
import retrofit2.HttpException

class GetMoviesUseCase @Inject constructor(
    private val repository: MovieRepository,
    private val movieDao: MovieDao,
    @DispatcherModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    operator fun invoke(): Flow<Resource<List<Movie>>> = flow {
        try {
            emit(Resource.Loading())
            val movies = repository.getMovies()
                .map { it.toMovieEntity() }

            withContext(ioDispatcher) {
                movieDao.insert(movies)
            }
            emit(
                Resource.Success(
                    movieDao.selectAll().map { it.toMovie() }
                )
            )
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "HttpException in ${this.javaClass.name}"))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: "IOException in ${this.javaClass.name}"))
        }
    }
}