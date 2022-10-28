package mn.turbo.marvel.presenter.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mn.turbo.marvel.common.collectLatestLifecycleFlow
import mn.turbo.marvel.databinding.FragmentTvShowBinding
import mn.turbo.marvel.presenter.tvshow.adapter.TvShowsAdapter
import mn.turbo.marvel.presenter.tvshow.viewmodel.TvShowViewModel

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
            adapter.submitList(state.data)
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
