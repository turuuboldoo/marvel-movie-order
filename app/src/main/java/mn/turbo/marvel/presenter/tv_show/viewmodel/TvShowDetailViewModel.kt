package mn.turbo.marvel.presenter.tv_show.viewmodel

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
import mn.turbo.marvel.domain.model.TvShow
import mn.turbo.marvel.domain.usecase.tvshow.GetTvShowDetailUseCase

@HiltViewModel
class TvShowDetailViewModel @Inject constructor(
    private val useCase: GetTvShowDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _tvShowState = MutableStateFlow(UiState<TvShow>())
    val tvShowState = _tvShowState.asStateFlow()

    init {
        savedStateHandle.get<Int>(Constant.PARAM_TV_SHOW_ID)?.let { getTvShowDetail(it) }
    }

    private fun getTvShowDetail(tvShowId: Int) {
        useCase(tvShowId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _tvShowState.value = UiState(
                        data = result.data
                    )
                }
                is Resource.Loading -> {
                    _tvShowState.value = UiState(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _tvShowState.value = UiState(
                        error = result.message ?: "some error in ${this.javaClass.name}"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}