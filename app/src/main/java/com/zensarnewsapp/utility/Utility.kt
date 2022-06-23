package com.zensarnewsapp.utility

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.preference.PreferenceManager
import androidx.core.content.ContextCompat.getSystemService
import com.zensarnewsapp.R

class Utility {

    companion object {

        private lateinit var sharedPreferences: SharedPreferences
        private lateinit var editor: SharedPreferences.Editor

         fun checkForInternet(context: Context): Boolean {

            // register activity with the connectivity manager service
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val network = connectivityManager.activeNetwork ?: return false
                val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

                return when {
                    // WiFi has network connectivity
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                    // Cellular has network connectivity
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                    else -> false
                }
            } else {
                // if the android version is below M
                @Suppress("DEPRECATION") val networkInfo =
                    connectivityManager.activeNetworkInfo ?: return false
                @Suppress("DEPRECATION")
                return networkInfo.isConnected
            }
        }


        fun setSharedPrefernce(context: Context, key: String, value:String){
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            editor = sharedPreferences.edit()
            editor.putString(key,value)
            editor.apply()
            editor.commit()

        }

        fun getSharedPrefernce(context: Context,key: String) : String{
            val data = sharedPreferences.getString("key", "").toString()
            return data
        }
    }


}