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
import mn.turbo.marvel.presenter.tv_show.viewmodel.TvShowViewModel

@AndroidEntryPoint
class TvShowListFragment : Fragment() {

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
        collectLatestLifecycleFlow(viewModel.tvShowListState) { state ->
            state.tvShows.forEach {
                Log.w("123123", "TvShowFragment ${it.title}")
            }
        }

        // navigate by id to detail fragment
//        findNavController().navigate(
//            TvShowListFragmentDirections
//                .actionTvShowFragmentToTvShowDetailFragment(1)
//        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
