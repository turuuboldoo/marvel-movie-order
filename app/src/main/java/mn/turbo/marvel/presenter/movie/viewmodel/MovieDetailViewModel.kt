package mn.turbo.marvel.presenter.movie.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import mn.turbo.marvel.common.Resource
import mn.turbo.marvel.domain.usecase.movie.GetMovieDetailUseCase
import mn.turbo.marvel.presenter.movie.state.MovieDetailState

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val useCase: GetMovieDetailUseCase
) : ViewModel() {

    private val _movieListState = MutableStateFlow(MovieDetailState())
    val movieListState = _movieListState.asStateFlow()

    fun getMovieDetail(movieId: Int) {
        useCase(movieId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _movieListState.value = MovieDetailState(
                        movie = result.data
                    )
                }
                is Resource.Loading -> {
                    _movieListState.value = MovieDetailState(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _movieListState.value = MovieDetailState(
                        error = result.message ?: "some error in ${this.javaClass.name}"
                    )
                }
            }
        }
    }
}