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
import mn.turbo.marvel.domain.usecase.tvshow.GetTvShowDetailUseCase
import mn.turbo.marvel.presenter.tv_show.state.TvShowDetailState

@HiltViewModel
class TvShowDetailViewModel @Inject constructor(
    private val useCase: GetTvShowDetailUseCase
) : ViewModel() {

    private val _tvShowState = MutableStateFlow(TvShowDetailState())
    val tvShowState = _tvShowState.asStateFlow()

    fun getTvShowDetail(tvShowId: Int) {
        useCase(tvShowId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _tvShowState.value = TvShowDetailState(
                        tvShow = result.data
                    )
                }
                is Resource.Loading -> {
                    _tvShowState.value = TvShowDetailState(
                        isLoading = true
                    )
                }
                is Resource.Error -> {
                    _tvShowState.value = TvShowDetailState(
                        error = result.message ?: "some error in ${this.javaClass.name}"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}