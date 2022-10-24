package mn.turbo.marvel.domain.usecase.tvshow

import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mn.turbo.marvel.common.Resource
import mn.turbo.marvel.domain.model.TvShow
import mn.turbo.marvel.domain.repository.TvShowRepository
import retrofit2.HttpException

class GetTvShowDetailUseCase @Inject constructor(
    private val repository: TvShowRepository,
) {
    operator fun invoke(tvShowId: Int): Flow<Resource<TvShow>> = flow {
        emit(Resource.Loading())
        try {
            val tvShow = repository.getTvShowsById(tvShowId)

            emit(Resource.Success(tvShow))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "HttpException in ${this.javaClass.name}"))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: "IOException in ${this.javaClass.name}"))
        }
    }
}