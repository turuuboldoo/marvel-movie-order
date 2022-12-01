package mn.turbo.marvel.presenter.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mn.turbo.marvel.R
import mn.turbo.marvel.common.extension.collectLatestLifecycleFlow
import mn.turbo.marvel.databinding.FragmentTvShowDetailBinding
import mn.turbo.marvel.presenter.tvshow.viewmodel.TvShowDetailViewModel

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
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_tv_show_detail,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).apply {
            setSupportActionBar(binding.mToolbar)
            supportActionBar?.let { actionbar ->
                actionbar.setDisplayHomeAsUpEnabled(true)
                actionbar.setHomeAsUpIndicator(R.drawable.ic_round_arrow_back_24)
            }
        }

        collectLatestLifecycleFlow(viewModel.tvShowState) { uiState ->
            binding.apply {
                tvShow = uiState.data
                state = uiState
            }
        }

        with(binding) {
            trailerButton.setOnClickListener {
                findNavController().navigate(
                    TvShowDetailFragmentDirections
                        .actionToVideoPlayer(binding.tvShow?.trailerUrl)
                )
            }

            descTextView.setOnClickListener {
                if (descTextView.maxLines == 3) {
                    descTextView.maxLines = 100
                } else {
                    descTextView.maxLines = 3
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
