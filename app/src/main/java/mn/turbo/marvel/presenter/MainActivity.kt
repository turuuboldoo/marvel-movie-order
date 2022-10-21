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
import mn.turbo.marvel.presenter.tv_show.TvShowFragmentDirections

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        navController = navHostFragment.findNavController()

        setupWithNavController(binding.bottomNavigationView, navController)

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuHome -> {
                    navController.navigate(
                        TvShowFragmentDirections.actionListenFragmentToHomeFragment()
                    )
                    true
                }
                R.id.menuListen -> {
                    navController.navigate(
                        MovieListFragmentDirections.actionHomeFragmentToListenFragment()
                    )
                    true
                }
                else -> false
            }
        }
    }
}
