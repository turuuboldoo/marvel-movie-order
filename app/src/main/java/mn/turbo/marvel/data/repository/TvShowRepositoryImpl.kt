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
        val tvShows = tvShowDao.selectAll().map { it.toTvShow() }

        return tvShows.ifEmpty {
            val tvShowsFromRemote = api.getTvShows().data

            withContext(ioDispatcher) {
                tvShowDao.insert(
                    tvShowEntity = tvShowsFromRemote.map { it.toTvShowEntity() }
                )
            }

            tvShowsFromRemote.map { it.toTvShow() }
        }
    }

    override suspend fun getTvShowsById(tvShowId: Int): TvShow =
        withContext(ioDispatcher) {
            tvShowDao.selectTvShowById(tvShowId).toTvShow()
        }
}
