package mn.turbo.marvel.presenter.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mn.turbo.marvel.R
import mn.turbo.marvel.common.collectLatestLifecycleFlow
import mn.turbo.marvel.common.cropTop
import mn.turbo.marvel.databinding.FragmentMovieDetailBinding
import mn.turbo.marvel.presenter.movie.adapter.RelatedAdapter
import mn.turbo.marvel.presenter.movie.viewmodel.MovieDetailViewModel

@AndroidEntryPoint
class MovieDetailFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var relatedAdapter: RelatedAdapter

    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_movie_detail,
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
                actionbar.setDisplayShowTitleEnabled(false)
                actionbar.setDisplayHomeAsUpEnabled(true)
                actionbar.setHomeAsUpIndicator(R.drawable.ic_round_arrow_back_24)
            }
        }

        setClickListener()
        setAdapter(deviceWidth)

        collectLatestLifecycleFlow(viewModel.movieListState) { state ->
            binding.apply {
                binding.mProgressBar.isVisible = state.isLoading
                binding.trailerButton.isVisible = !state.isLoading
                movie = state.data
                coverImageView.cropTop(state.data?.coverUrl, deviceWidth, deviceWidth)
                relatedRecyclerView.adapter = relatedAdapter

                if (state.data?.relatedMovie?.isEmpty() == true) {
                    relatedTextView.visibility = View.GONE
                }
            }

            relatedAdapter.submitList(state.data?.relatedMovie)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(p0: View?) {
        binding.run {
            when (p0) {
                trailerButton -> {
                    findNavController().navigate(
                        MovieDetailFragmentDirections
                            .actionToVideoPlayer(binding.movie?.trailerUrl)
                    )
                }
                descTextView -> {
                    if (descTextView.maxLines == 3) {
                        descTextView.maxLines = 100
                    } else {
                        descTextView.maxLines = 3
                    }
                }
            }
        }
    }

    private fun setClickListener() {
        binding.let { layout ->
            layout.descTextView.setOnClickListener(this)
            layout.titleTextView.setOnClickListener(this)
            layout.trailerButton.setOnClickListener(this)
        }
    }

    private fun setAdapter(deviceWidth: Int) {
        relatedAdapter = RelatedAdapter(deviceWidth) { relatedMovie ->
            findNavController().navigate(
                MovieDetailFragmentDirections
                    .actionMovieDetailFragmentSelf(relatedMovie.id)
            )
        }
    }
}
