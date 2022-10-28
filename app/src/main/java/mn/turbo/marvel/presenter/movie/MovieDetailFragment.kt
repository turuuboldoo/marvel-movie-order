package mn.turbo.marvel.presenter.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt
import mn.turbo.marvel.R
import mn.turbo.marvel.common.collectLatestLifecycleFlow
import mn.turbo.marvel.common.cropTop
import mn.turbo.marvel.databinding.FragmentMovieDetailBinding
import mn.turbo.marvel.presenter.movie.viewmodel.MovieDetailViewModel

@AndroidEntryPoint
class MovieDetailFragment : Fragment(), OnClickListener {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

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

        setClickListener()

        collectLatestLifecycleFlow(viewModel.movieListState) { state ->
            binding.movie = state.data

            binding.coverImageView.cropTop(
                url = state.data?.coverUrl,
                width = deviceWidth,
                height = (deviceWidth / 1.5).roundToInt()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(p0: View?) {
        binding.run {
            when (p0) {
                coverImageView -> {
                    Log.w("123123", "coverImageView - ${binding.movie?.trailerUrl}")
                    findNavController().navigate(
                        MovieDetailFragmentDirections
                            .actionMovieDetailFragmentToVideoPlayerFragment(
                                videoUrl = binding.movie?.trailerUrl ?: ""
                            )
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
            layout.coverImageView.setOnClickListener(this)
        }
    }
}