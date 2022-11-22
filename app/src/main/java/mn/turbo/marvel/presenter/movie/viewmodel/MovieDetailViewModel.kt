package mn.turbo.marvel.presenter.movie.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import mn.turbo.marvel.common.Constant
import mn.turbo.marvel.common.Resource
import mn.turbo.marvel.common.UiState
import mn.turbo.marvel.domain.model.Movie
import mn.turbo.marvel.domain.usecase.movie.GetMovieDetailUseCase

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val useCase: GetMovieDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _movieListState = MutableStateFlow(UiState<Movie>())
    val movieListState = _movieListState.asStateFlow()

    init {
        savedStateHandle.get<Int>(Constant.PARAM_MOVIE_ID)?.let { getMovieDetail(it) }
    }

    private fun getMovieDetail(movieId: Int) {
        useCase(movieId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _movieListState.value = UiState(
                        data = result.data
                    )
                }
                is Resource.Loading -> {
                    _movieListState.value = UiState(
                        isLoading = true
                    )
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
