package mn.turbo.marvel.data.repository

import javax.inject.Inject
import mn.turbo.marvel.data.remote.MarvelApi
import mn.turbo.marvel.data.remote.dto.TvShowDto
import mn.turbo.marvel.domain.repository.TvShowRepository

class TvShowRepositoryImpl @Inject constructor(
    private val api: MarvelApi
) : TvShowRepository {
    override suspend fun getTvShows(): List<TvShowDto> {
        return api.getTvShows().data
    }

    override suspend fun getTvShowsById(tvShowId: Int): TvShowDto {
        return api.getTvShowById(tvShowId)
    }
}