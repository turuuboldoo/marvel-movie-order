package mn.turbo.marvel.presenter.tv_show

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import mn.turbo.marvel.common.collectLatestLifecycleFlow
import mn.turbo.marvel.databinding.FragmentTvShowDetailBinding
import mn.turbo.marvel.presenter.tv_show.viewmodel.TvShowDetailViewModel

@AndroidEntryPoint
class TvShowDetailFragment : Fragment() {

    private var _binding: FragmentTvShowDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TvShowDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvShowDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectLatestLifecycleFlow(viewModel.tvShowState) { state ->
            Log.w("123123", "TvShowDetailFragment - ${state.data?.title}")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}