package mn.turbo.marvel.presenter.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import mn.turbo.marvel.common.collectLatestLifecycleFlow
import mn.turbo.marvel.databinding.FragmentMovieListBinding
import mn.turbo.marvel.presenter.movie.viewmodel.MovieViewModel

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getMovies()
    }

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
        collectLatestLifecycleFlow(viewModel.movieListState) { state ->
            state.data?.forEach {
                Log.w("123123", "MovieListFragment ${it.title}")
            }
        }

        // navigate by id to detail fragment
//        findNavController().navigate(
//            MovieListFragmentDirections
//                .actionMovieFragmentToMovieDetailFragment(1)
//        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}