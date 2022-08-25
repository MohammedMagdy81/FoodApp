package com.example.foodapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.foodapp.R
import com.example.foodapp.viewModel.HomeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

     val homeViewModel:HomeViewModel by lazy {
         ViewModelProvider(this).get(HomeViewModel::class.java)
     }

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // this code to link bottom navigation with navController
        val bottomNavigation= findViewById<BottomNavigationView>(R.id.bottom_nav)
        val navController= Navigation.findNavController(this, R.id.fragment_container)

        NavigationUI.setupWithNavController(bottomNavigation,navController)
    }
}