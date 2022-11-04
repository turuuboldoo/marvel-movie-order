package mn.turbo.marvel.presenter.movie.list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import mn.turbo.marvel.common.Resource
import mn.turbo.marvel.common.UiState
import mn.turbo.marvel.domain.model.Movie
import mn.turbo.marvel.domain.usecase.movie.GetMoviesUseCase

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
) : ViewModel() {

    private val _movieListState = mutableStateOf(UiState<List<Movie>>())
    val movieListState = _movieListState

    init {
        getMovies()
    }

    private fun getMovies() {
        getMoviesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _movieListState.value = UiState(
                        data = result.data ?: emptyList()
                    )
                }
                is Resource.Loading -> {
                    _movieListState.value = UiState(isLoading = true)
                }
                is Resource.Error -> {
                    _movieListState.value = UiState(
                        error = result.message ?: "some error in ${this.javaClass.name}"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}