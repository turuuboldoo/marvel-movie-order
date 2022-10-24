package mn.turbo.marvel.presenter.tv_show

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mn.turbo.marvel.common.collectLatestLifecycleFlow
import mn.turbo.marvel.databinding.FragmentTvShowBinding
import mn.turbo.marvel.presenter.tv_show.adapter.TvShowsAdapter
import mn.turbo.marvel.presenter.tv_show.viewmodel.TvShowViewModel

@AndroidEntryPoint
class TvShowListFragment : Fragment() {

    private var _binding: FragmentTvShowBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TvShowViewModel by viewModels()

    private var adapter = TvShowsAdapter { tvShow ->
        navigateToDetail(tvShow.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvShowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter

        collectLatestLifecycleFlow(viewModel.tvShowListState) { state ->
            state.tvShows.forEach {
                Log.w("123123", "TvShowFragment ${it.title}")
            }
            adapter.submitList(state.tvShows)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToDetail(movieId: Int) {
        findNavController().navigate(
            TvShowListFragmentDirections
                .actionTvShowFragmentToTvShowDetailFragment(movieId)
        )
    }
}
