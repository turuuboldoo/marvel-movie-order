package mn.turbo.marvel.presenter.movie.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import mn.turbo.marvel.common.Resource
import mn.turbo.marvel.domain.use_case.movie.GetMoviesUseCase
import mn.turbo.marvel.presenter.movie.state.MovieListState

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val coroutineScope: CoroutineScope
) : ViewModel() {

    private val _movieListState = MutableStateFlow(MovieListState())
    val movieListState = _movieListState.asStateFlow()

    init {
        getMovies()
    }

    private fun getMovies() {
        getMoviesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _movieListState.value = MovieListState(
                        movies = result.data ?: emptyList()
                    )
                }
                is Resource.Loading -> {
                    _movieListState.value = MovieListState(isLoading = true)
                }
                is Resource.Error -> {
                    _movieListState.value = MovieListState(
                        error = result.message ?: "some error in ${this.javaClass.name}"
                    )
                }
            }
        }.launchIn(coroutineScope)
    }
}