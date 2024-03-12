package com.example.ramzancalender.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.ramzancalender.MainActivity
import com.example.ramzancalender.databinding.FragmentGetLocationBinding
import com.example.ramzancalender.helper.NetworkHelper
import com.example.ramzancalender.putCity
import com.example.ramzancalender.putLatLng
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.Locale


class GetLocationFragment : Fragment() {
    lateinit var binding: FragmentGetLocationBinding


    @Suppress("DEPRECATION")
    fun Geocoder.getAddress(
        latitude: Double,
        longitude: Double,
        address: (android.location.Address?) -> Unit
    ) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getFromLocation(latitude, longitude, 1) { address(it.firstOrNull()) }
            return
        }

        try {
            address(getFromLocation(latitude, longitude, 1)?.firstOrNull())
        } catch (e: Exception) {
            Log.d("hassError", e.message!!)
            //will catch if there is an internet problem
            address(null)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGetLocationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val network = NetworkHelper(requireContext())
        binding.button.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    if (network.isNetworkAvailable()) {
                        withContext(Dispatchers.Main) {
                            checkLocationPermissionsAndEnablement()
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                requireContext(),
                                "Please Turn On Internet",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                }
            }


        }
        binding.btnNexts.setOnClickListener {
            val locationClient =
                LocationServices.getFusedLocationProviderClient(requireContext())
            lifecycleScope.launch {
                val priority = if (true) {
                    Priority.PRIORITY_HIGH_ACCURACY
                } else {
                    Priority.PRIORITY_BALANCED_POWER_ACCURACY
                }
                val result = locationClient.getCurrentLocation(
                    priority,
                    CancellationTokenSource().token,
                ).await()
                result?.let { fetchedLocation ->

                    val location = fetchedLocation
                    val sharedPreferences =
                        requireActivity().getSharedPreferences(
                            "main",
                            Context.MODE_PRIVATE
                        )
                    val editor = sharedPreferences.edit()
                    editor.putLatLng(location.latitude, location.longitude)
                    Geocoder(requireContext(), Locale("in"))
                        .getAddress(
                            location.latitude,
                            location.longitude
                        ) { address: android.location.Address? ->
                            if (address != null) {
                                startActivity(
                                    Intent(
                                        requireActivity(),
                                        MainActivity::class.java
                                    )
                                ).also {
                                    requireActivity().finish()
                                }
                                editor.putCity(address.locality)
                            }
                        }

                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        checkLocationPermissionsAndEnablement(false)

    }


    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            MY_PERMISSIONS_REQUEST_LOCATION
        )
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(
                            requireActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        checkLocationPermissionsAndEnablement()
                    }


                } else {
                    Toast.makeText(requireActivity(), "permission denied", Toast.LENGTH_LONG).show()

                    // Check if we are in a state where the user has denied the permission and
                    // selected Don't ask again
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(
                            requireActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    ) {
                        startActivity(
                            Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", requireActivity().packageName, null),
                            ),
                        )
                    }
                }
                return
            }

        }
    }

    @SuppressLint("MissingPermission")
    private fun checkLocationPermissionsAndEnablement(doesShowRequest: Boolean = true) {
        // Check if permissions are granted
        if (hasLocationPermissions()) {
            if (!isLocationEnabled()) {
                showLocationSettingsDialog()
            } else {
                binding.button.visibility = View.GONE
                binding.btnNexts.visibility = View.VISIBLE
                binding.textView7.text = "All set press next to proceed"
            }
        } else {
            if (doesShowRequest) {
                requestLocationPermission()
            }

        }
    }

    private fun showLocationSettingsDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Location Services Required")
            .setMessage("Please EnableLocation to get accurate ramadan calender")
            .setCancelable(false)
            .setPositiveButton("ok") { dialog, which ->
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun hasLocationPermissions(): Boolean {
        return (ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = requireContext().getSystemService(LocationManager::class.java)
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    companion object {
        private const val MY_PERMISSIONS_REQUEST_LOCATION = 99
        private const val MY_PERMISSIONS_REQUEST_BACKGROUND_LOCATION = 66
    }
}