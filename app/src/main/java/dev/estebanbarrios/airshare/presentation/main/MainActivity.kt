package dev.estebanbarrios.airshare.presentation.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dev.estebanbarrios.airshare.R
import dev.estebanbarrios.airshare.data.entities.models.VideoGrouped
import dev.estebanbarrios.airshare.data.entities.utils.EmptyResponse
import dev.estebanbarrios.airshare.data.entities.utils.Loading
import dev.estebanbarrios.airshare.data.entities.utils.SuccessResponse
import dev.estebanbarrios.airshare.presentation.extensions.toGone
import dev.estebanbarrios.airshare.presentation.extensions.toVisible
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setUpNavigation()
    }

    override fun onSupportNavigateUp() = findNavController(R.id.fragNavHost).navigateUp()

    private fun setUpNavigation() {
        // Finding the Navigation Controller
        var navController = findNavController(R.id.fragNavHost)

        // Setting Navigation Controller with the BottomNavigationView
        bottomNavView.setupWithNavController(navController)

        // Setting Up ActionBar with Navigation Controller
        var appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.sendFragment,
                R.id.receiveFragment,
                R.id.historyFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }


}
