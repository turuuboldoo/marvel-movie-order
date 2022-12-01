package mn.turbo.marvel.presenter.movie

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
import javax.inject.Inject
import mn.turbo.marvel.R
import mn.turbo.marvel.common.ConnectivityObserver
import mn.turbo.marvel.common.extension.collectLatestLifecycleFlow
import mn.turbo.marvel.databinding.FragmentMovieBinding
import mn.turbo.marvel.presenter.movie.adapter.MovieAdapter
import mn.turbo.marvel.presenter.movie.viewmodel.MovieViewModel

@AndroidEntryPoint
class MovieFragment : Fragment() {

    @Inject
    lateinit var connectivityObserver: ConnectivityObserver

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieViewModel by viewModels()

    private var isNetworkConnected = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectLatestLifecycleFlow(connectivityObserver.observe()) { status ->
            isNetworkConnected = when (status) {
                ConnectivityObserver.Status.Available -> true
                else -> false
            }
        }

        val movieAdapter = MovieAdapter { movie ->
            if (isNetworkConnected) {
                findNavController().navigate(
                    MovieFragmentDirections
                        .actionToMovieDetailFragment(movie.id)
                )
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.no_internet),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.recyclerView.adapter = movieAdapter

        collectLatestLifecycleFlow(viewModel.movieListState) { state ->
            binding.progressBar.isVisible = state.isLoading
            movieAdapter.submitList(state.data)

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
