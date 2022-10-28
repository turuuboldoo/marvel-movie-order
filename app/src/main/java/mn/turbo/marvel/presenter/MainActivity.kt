package mn.turbo.marvel.presenter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import mn.turbo.marvel.R
import mn.turbo.marvel.databinding.ActivityMainBinding
import mn.turbo.marvel.presenter.movie.MovieListFragmentDirections
import mn.turbo.marvel.presenter.tvshow.TvShowListFragmentDirections

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavHost()
        bottomNavClickListener()
    }

    private fun setupNavHost() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        navController = navHostFragment.findNavController()

        setupWithNavController(binding.bottomNavigationView, navController)
    }

    private fun bottomNavClickListener() {
        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuHome -> {
                    navController.navigate(
                        TvShowListFragmentDirections.actionTvShowFragmentToMovieFragment()
                    )
                    true
                }
                R.id.menuListen -> {
                    navController.navigate(
                        MovieListFragmentDirections.actionMovieFragmentToTvShowFragment()
                    )
                    true
                }
                else -> false
            }
        }
    }
}
