package mn.turbo.marvel.presenter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import dagger.hilt.android.AndroidEntryPoint
import mn.turbo.marvel.R
import mn.turbo.marvel.data.local.preference.AppPreference
import mn.turbo.marvel.databinding.ActivityMainBinding

@OptIn(NavigationUiSaveStateControl::class)
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        setAppSetting()

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        navController = navHostFragment.findNavController()

        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController, false)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.movieFragment, R.id.tvShowFragment)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

    private fun setAppSetting() {
        AppPreference.getInstance()
            .apply {
                putDeviceWidth(resources.displayMetrics.widthPixels)
                putDeviceHeight(resources.displayMetrics.heightPixels)
            }
    }
}
