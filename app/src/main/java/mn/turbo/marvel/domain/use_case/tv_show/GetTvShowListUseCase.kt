package mn.turbo.marvel.domain.use_case.tv_show

import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mn.turbo.marvel.common.Resource
import mn.turbo.marvel.domain.model.TvShow
import mn.turbo.marvel.domain.repository.TvShowRepository
import retrofit2.HttpException

class GetTvShowListUseCase @Inject constructor(
    private val repository: TvShowRepository
) {
    operator fun invoke(): Flow<Resource<List<TvShow>>> = flow {
        try {
            emit(Resource.Loading())

            val tvShows = repository.getTvShows()
                .map { it.toTvShow() }

            emit(Resource.Success(tvShows))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "HttpException in ${this.javaClass.name}"))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: "IOException in ${this.javaClass.name}"))
        }
    }
}