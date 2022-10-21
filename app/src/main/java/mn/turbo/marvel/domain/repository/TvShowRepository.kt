package mn.turbo.marvel.domain.repository

import mn.turbo.marvel.data.remote.dto.TvShowDto

interface TvShowRepository {
    suspend fun getTvShows(): List<TvShowDto>
    suspend fun getTvShowsById(tvShowId: Int): TvShowDto
}