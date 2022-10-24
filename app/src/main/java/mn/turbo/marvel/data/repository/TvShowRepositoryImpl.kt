package mn.turbo.marvel.data.repository

import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import mn.turbo.marvel.data.local.dao.TvShowDao
import mn.turbo.marvel.data.remote.MarvelApi
import mn.turbo.marvel.di.IoDispatcher
import mn.turbo.marvel.domain.model.TvShow
import mn.turbo.marvel.domain.repository.TvShowRepository

class TvShowRepositoryImpl @Inject constructor(
    private val api: MarvelApi,
    private val tvShowDao: TvShowDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : TvShowRepository {

    override suspend fun getTvShows(): List<TvShow> {
        val tvShows = api.getTvShows().data
            .map { it.toTvShowEntity() }

        withContext(ioDispatcher) {
            tvShowDao.insert(tvShows)
        }

        return tvShowDao.selectAll().map { it.toTvShow() }
    }

    override suspend fun getTvShowsById(tvShowId: Int): TvShow =
        withContext(ioDispatcher) {
            tvShowDao.selectTvShowById(tvShowId).toTvShow()
        }
}