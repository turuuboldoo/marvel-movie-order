package mn.turbo.marvel.presenter.movie.viewmodel

import android.view.View
import android.widget.TextView
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
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
import mn.turbo.marvel.presenter.movie.MovieDetailFragmentDirections

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

    fun onDescClick(view: View) {
        val textView = view as TextView
        if (textView.maxLines == 3) {
            textView.maxLines = 100
        } else {
            textView.maxLines = 3
        }
    }

    fun launchTrailerFragment(view: View) {
        view.findNavController().navigate(
            MovieDetailFragmentDirections
                .actionToVideoPlayer(_movieListState.value.data?.trailerUrl)
        )
    }
}
