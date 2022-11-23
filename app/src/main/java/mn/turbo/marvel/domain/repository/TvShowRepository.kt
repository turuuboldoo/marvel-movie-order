package mn.turbo.marvel.domain.repository

import mn.turbo.marvel.domain.model.TvShow

interface TvShowRepository {
    suspend fun getTvShows(): List<TvShow>
    suspend fun getTvShowsById(tvShowId: Int): TvShow
}
