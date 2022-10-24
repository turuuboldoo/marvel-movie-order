package mn.turbo.marvel.presenter.movie.state

import mn.turbo.marvel.domain.model.Movie

data class MovieListState(
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val error: String = ""
)

data class MovieDetailState(
    val isLoading: Boolean = false,
    val movie: Movie? = null,
    val error: String = ""
)