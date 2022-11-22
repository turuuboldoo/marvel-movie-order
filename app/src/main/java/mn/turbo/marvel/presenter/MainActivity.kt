package mn.turbo.marvel.presenter

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import mn.turbo.marvel.R
import mn.turbo.marvel.common.ConnectivityObserver
import mn.turbo.marvel.common.ConnectivityObserver.Status.*
import mn.turbo.marvel.common.collectLatestLifecycleFlow
import mn.turbo.marvel.databinding.ActivityMainBinding
import mn.turbo.marvel.presenter.movie.MovieListFragmentDirections
import mn.turbo.marvel.presenter.tvshow.TvShowListFragmentDirections

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    @Inject
    lateinit var connectionObserver: ConnectivityObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavHost()
        bottomNavClickListener()

        collectLatestLifecycleFlow(connectionObserver.observe()) {
            when (it) {
                Available -> {
                    Toast.makeText(this, "Internet Available!", Toast.LENGTH_SHORT).show()
                }
                Losing -> {
                    Toast.makeText(this, "Internet Losing!", Toast.LENGTH_SHORT).show()
                }
                Unavailable -> {
                    Toast.makeText(this, "Internet Unavailable!", Toast.LENGTH_SHORT).show()
                }
                Lost -> {
                    Toast.makeText(this, "Internet Lost!", Toast.LENGTH_SHORT).show()
                }
            }
        }
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
