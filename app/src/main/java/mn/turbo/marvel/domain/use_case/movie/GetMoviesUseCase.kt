package mn.turbo.marvel.domain.use_case.movie

import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mn.turbo.marvel.common.Resource
import mn.turbo.marvel.domain.model.Movie
import mn.turbo.marvel.domain.repository.MovieRepository
import retrofit2.HttpException

class GetMoviesUseCase @Inject constructor(
    private val repository: MovieRepository,
) {
    operator fun invoke(): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading())
        try {
            val movies = repository.getMovies()

            emit(Resource.Success(movies))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "HttpException in ${this.javaClass.name}"))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: "IOException in ${this.javaClass.name}"))
        }
    }
}