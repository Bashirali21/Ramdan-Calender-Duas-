package com.example.ramzancalender

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.ramzancalender.fragments.shia
import com.example.ramzancalender.fragments.sunni
import com.example.ramzancalender.viewmodel.MainAppViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val viewModel by viewModels<MainAppViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPreferences = getSharedPreferences("main", Context.MODE_PRIVATE)
        val location = sharedPreferences.getLatLng()
        val sect = sharedPreferences.getSect()
        if (location.first != 0.0) {
            if (sect == sunni) {
                viewModel.fetchRamzanData(
                    latitude = location.first,
                    longitude = location.second,
                    1,
                    firstMonth = 3,
                    year = 2024,
                    secondMonth = 4
                )

            } else
                if (sect == shia) {
                    viewModel.fetchRamzanData(
                        latitude = location.first,
                        longitude = location.second,
                        1,
                        firstMonth = 3,
                        year = 2024,
                        secondMonth = 4
                    )
                } else {
                    viewModel.fetchRamadanDataBySchool(
                        latitude = location.first,
                        longitude = location.second,
                        1,
                        firstMonth = 3,
                        year = 2024,
                        secondMonth = 4
                    )
                }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController().navigateUp() || super.onSupportNavigateUp()
    }

    private fun findNavController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController
    }


}