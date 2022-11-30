package mn.turbo.marvel.presenter.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mn.turbo.marvel.common.collectLatestLifecycleFlow
import mn.turbo.marvel.databinding.FragmentTvShowBinding
import mn.turbo.marvel.presenter.tvshow.adapter.TvShowsAdapter
import mn.turbo.marvel.presenter.tvshow.viewmodel.TvShowViewModel

@AndroidEntryPoint
class TvShowFragment : Fragment() {

    private var _binding: FragmentTvShowBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TvShowViewModel by viewModels()

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
        val tvShowsAdapter = TvShowsAdapter { tvShow ->
            findNavController().navigate(
                TvShowFragmentDirections
                    .actionToTvShowDetailFragment(tvShow.id)
            )
        }

        binding.recyclerView.adapter = tvShowsAdapter

        collectLatestLifecycleFlow(viewModel.tvShowListState) { state ->
            binding.progressBar.isVisible = state.isLoading
            tvShowsAdapter.submitList(state.data)

            if (state.error.isNotBlank()) {
                Toast.makeText(requireContext(), state.error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
