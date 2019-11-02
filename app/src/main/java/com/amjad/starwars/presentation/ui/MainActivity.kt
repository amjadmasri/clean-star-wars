package com.amjad.starwars.presentation.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.amjad.starwars.R
import com.amjad.starwars.presentation.ui.base.BaseActivity

class MainActivity : BaseActivity() {

    private lateinit var navController: NavController
    override fun getLayoutId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        navController = Navigation.findNavController(
            this,
            R.id.nav_host_fragment
        )
    }
}
