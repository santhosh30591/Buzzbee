package dev.miyatech.buzzbee.netwoork.getlocation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.ConnectivityManager

class GpsStatusReceiver(private val onGpsStatusChanged: (Intent?) -> Unit) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == LocationManager.PROVIDERS_CHANGED_ACTION || intent?.action == ConnectivityManager.CONNECTIVITY_ACTION) {
//            val locationManager =
//                context?.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
//            val isGpsEnabled =
//                locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) ?: false
            onGpsStatusChanged(intent)
        }


    }
}