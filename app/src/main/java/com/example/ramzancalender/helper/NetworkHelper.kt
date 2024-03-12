package com.example.ramzancalender.helper

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import java.net.Socket
import javax.inject.Singleton

import java.io.IOException
import java.net.InetSocketAddress


/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
@Suppress("DEPRECATION")
@Singleton
class NetworkHelper(private val context: Context) {
    fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                return when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        try {
                            val socket = Socket()
                            socket.connect(InetSocketAddress("8.8.8.8", 53), 1500)
                            socket.close()
                            true
                        } catch (e: IOException) {
                            false
                        }
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        try {
                            val socket = Socket()
                            socket.connect(InetSocketAddress("8.8.8.8", 53), 1500)
                            socket.close()
                            true
                        } catch (e: IOException) {
                            false
                        }
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> {
                        try {
                            val socket = Socket()
                            socket.connect(InetSocketAddress("8.8.8.8", 53), 1500)
                            socket.close()
                            true
                        } catch (e: IOException) {
                            false
                        }
                    }
                    else -> false
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                try {
                    val socket = Socket()
                    socket.connect(InetSocketAddress("8.8.8.8", 53), 1500)
                    socket.close()
                    true
                } catch (e: IOException) {
                    false
                }
            }
        }
        return false
    }
}