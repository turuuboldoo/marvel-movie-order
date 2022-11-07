package mn.turbo.marvel.presenter.movie.detail

import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
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

    private val _movieState = mutableStateOf(UiState<Movie>())
    val movieState = _movieState

    init {
        savedStateHandle.get<String>(Constant.PARAM_MOVIE_ID)?.let {
            if (it.isDigitsOnly()) {
                getMovieDetail(it.toInt())
            }
        }
    }

    private fun getMovieDetail(movieId: Int) {
        useCase(movieId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _movieState.value = UiState(
                        data = result.data
                    )
                }
                is Resource.Loading -> {
                    _movieState.value = UiState(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _movieState.value = UiState(
                        error = result.message ?: "some error in ${this.javaClass.name}"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}