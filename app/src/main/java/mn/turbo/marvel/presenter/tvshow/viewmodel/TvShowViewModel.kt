package mn.turbo.marvel.presenter.tvshow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import mn.turbo.marvel.common.Resource
import mn.turbo.marvel.common.UiState
import mn.turbo.marvel.domain.model.TvShow
import mn.turbo.marvel.domain.usecase.tvshow.GetTvShowListUseCase

@HiltViewModel
class TvShowViewModel @Inject constructor(
    private val useCase: GetTvShowListUseCase
) : ViewModel() {

    private val _tvShowListState = MutableStateFlow(UiState<List<TvShow>>())
    val tvShowListState = _tvShowListState.asStateFlow()

    init {
        getTvShows()
    }

    private fun getTvShows() {
        useCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _tvShowListState.value = UiState(
                        data = result.data ?: emptyList()
                    )
                }
                is Resource.Loading -> {
                    _tvShowListState.value = UiState(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _tvShowListState.value = UiState(
                        error = result.message ?: "some error in ${this.javaClass.name}"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}