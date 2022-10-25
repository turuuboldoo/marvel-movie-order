package mn.turbo.marvel.presenter.movie.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import mn.turbo.marvel.common.Resource
import mn.turbo.marvel.common.UiState
import mn.turbo.marvel.domain.model.Movie
import mn.turbo.marvel.domain.usecase.movie.GetMovieDetailUseCase

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val useCase: GetMovieDetailUseCase
) : ViewModel() {

    private val _movieListState = MutableStateFlow(UiState<Movie>())
    val movieListState = _movieListState.asStateFlow()

    fun getMovieDetail(movieId: Int) {
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
        }
    }
}