package mn.turbo.marvel.presenter.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import mn.turbo.marvel.R
import mn.turbo.marvel.common.collectLatestLifecycleFlow
import mn.turbo.marvel.common.cropTop
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
        val deviceWidth = requireContext().resources.displayMetrics.widthPixels

        (activity as AppCompatActivity).apply {
            setSupportActionBar(binding.mToolbar)
            supportActionBar?.let { actionbar ->
                actionbar.setDisplayHomeAsUpEnabled(true)
                actionbar.setHomeAsUpIndicator(R.drawable.ic_round_arrow_back_24)
            }
        }

        collectLatestLifecycleFlow(viewModel.tvShowState) { state ->
            binding.apply {
                tvShow = state.data
                coverImageView.cropTop(state.data?.coverUrl, deviceWidth, deviceWidth)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
