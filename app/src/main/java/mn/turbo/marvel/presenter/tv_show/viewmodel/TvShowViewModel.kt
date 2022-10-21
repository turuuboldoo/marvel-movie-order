package mn.turbo.marvel.presenter.tv_show.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import mn.turbo.marvel.common.Resource
import mn.turbo.marvel.domain.use_case.tv_show.GetTvShowListUseCase
import mn.turbo.marvel.presenter.tv_show.state.TvShowListState

@HiltViewModel
class TvShowViewModel @Inject constructor(
    private val useCase: GetTvShowListUseCase
) : ViewModel() {

    private val _tvShowListState = MutableStateFlow(TvShowListState())
    val tvShowListState = _tvShowListState.asStateFlow()

    init {
        getTvShows()
    }

    private fun getTvShows() {
        useCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _tvShowListState.value = TvShowListState(
                        tvShows = result.data ?: emptyList()
                    )
                }
                is Resource.Loading -> {
                    _tvShowListState.value = TvShowListState(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _tvShowListState.value = TvShowListState(
                        error = result.message ?: "some error in ${this.javaClass.name}"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}