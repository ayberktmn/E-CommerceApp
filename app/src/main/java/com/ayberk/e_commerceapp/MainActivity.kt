package com.ayberk.e_commerceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.ayberk.e_commerceapp.common.gone
import com.ayberk.e_commerceapp.common.visible
import com.ayberk.e_commerceapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNav, navController)

        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->

            when (destination.id) {
                R.id.splashFragment,
                R.id.signInFragment,
                R.id.signUpFragment, -> {
                    binding.bottomNav.gone()
                    binding.floatingActionButton.gone()
                }

                else -> {
                    binding.bottomNav.visible()
                    binding.floatingActionButton.visible()

                    binding.bottomNav.setOnItemSelectedListener { item ->
                        when (item.itemId) {
                            R.id.profile -> {
                                navController.navigate(R.id.profileFragment)
                                true
                            }
                            R.id.home -> {
                                navController.navigate(R.id.homeFragment)
                                true
                            }
                            else -> false
                        }
                    }
                }
            }
        }

        binding.floatingActionButton.setOnClickListener {
            // navController.navigate(R.id.searchFragment)
        }
    }
}