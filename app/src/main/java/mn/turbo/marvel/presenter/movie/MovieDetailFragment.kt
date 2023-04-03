package mn.turbo.marvel.presenter.movie

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
import mn.turbo.marvel.databinding.FragmentMovieDetailBinding
import mn.turbo.marvel.presenter.movie.adapter.RelatedAdapter
import mn.turbo.marvel.presenter.movie.viewmodel.MovieDetailViewModel

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

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

        (activity as AppCompatActivity).apply {
            setSupportActionBar(binding.mToolbar)
            supportActionBar?.let { actionbar ->
                actionbar.setDisplayShowTitleEnabled(false)
                actionbar.setDisplayHomeAsUpEnabled(true)
                actionbar.setHomeAsUpIndicator(R.drawable.ic_round_arrow_back_24)
            }
        }

        setAdapter()

        collectLatestLifecycleFlow(viewModel.movieListState) { uiState ->
            binding.apply {
                lifecycleOwner = this@MovieDetailFragment
                movieDetailViewModel = viewModel
                state = uiState
                movie = uiState.data

                relatedRecyclerView.adapter = relatedAdapter
            }

            relatedAdapter.submitList(uiState.data?.relatedMovie)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setAdapter() {
        relatedAdapter = RelatedAdapter(
            deviceWidth = requireContext().resources.displayMetrics.widthPixels
        ) { relatedMovie ->
            findNavController().navigate(
                MovieDetailFragmentDirections
                    .actionMovieDetailFragmentSelf(relatedMovie.id)
            )
        }
    }
}
