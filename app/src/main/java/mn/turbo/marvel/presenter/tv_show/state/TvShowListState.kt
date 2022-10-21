package mn.turbo.marvel.presenter.tv_show.state

import mn.turbo.marvel.domain.model.TvShow

data class TvShowListState(
    val isLoading: Boolean = false,
    val tvShows: List<TvShow> = emptyList(),
    val error: String = ""
)