package mn.turbo.marvel.presenter.movie

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mn.turbo.marvel.R
import mn.turbo.marvel.common.collectLatestLifecycleFlow
import mn.turbo.marvel.databinding.FragmentMovieListBinding
import mn.turbo.marvel.presenter.movie.adapter.MovieAdapter
import mn.turbo.marvel.presenter.movie.viewmodel.MovieViewModel

@AndroidEntryPoint
class MovieListFragment : Fragment(), MenuProvider {

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity)
            .apply {
                setSupportActionBar(binding.toolbar)
                supportActionBar?.title = ""
            }

        val movieAdapter = MovieAdapter { movie ->
            findNavController().navigate(
                MovieListFragmentDirections
                    .actionMovieFragmentToMovieDetailFragment(movie.id)
            )
        }

        binding.recyclerView.adapter = movieAdapter

        collectLatestLifecycleFlow(viewModel.movieListState) { state ->
            binding.progressBar.isVisible = state.isLoading
            movieAdapter.submitList(state.data)
        }

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(
            this,
            viewLifecycleOwner,
            Lifecycle.State.RESUMED
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.movie_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.menuFilter -> {
                Toast.makeText(requireContext(), "menu filter", Toast.LENGTH_SHORT).show()
                true
            }
            else -> false
        }
    }
}