package dev.miyatech.buzzbee.home


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import dev.miyatech.buzzbee.netwoork.getlocation.GpsStatusReceiver
import dev.miyatech.buzzbee.netwoork.getlocation.LocationTrack


@SuppressLint("MissingPermission")
@Composable
fun LocationPermission(navController: NavController) {
    var context = LocalContext.current


    var latitude by remember { mutableStateOf(0.0) }

    var location = LocationTrack(context)


    val locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
    val loca = locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) ?: false
    val isGpsEnabled = remember { mutableStateOf(loca) }
    val isInterNet = remember { mutableStateOf(false) }


    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()


    ) {


            permissions ->
        // Handle Permission granted/rejected
        permissions.entries.forEach {
            val permissionName = it.key
            val isGranted = it.value

            println(" key " + permissionName + " and val " + isGranted)

            if (permissionName.equals(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)) {
                val isGranted = it.value
                if (isGranted) {
                    // Permission is granted

                } else {
                    // Permission is denied
                }
            } else if (permissionName.equals(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) {
                val isGranted = it.value

                isGpsEnabled.value =
                    locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) ?: false

                if (isGranted) {

                } else {
                    latitude = 0.00
                    // Permission is denied
                }
            }


        }
    }
    val settingResultRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        {


            isGpsEnabled.value =
                locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) ?: false

            Log.i(
                "ammu",
                "TEST 1 : ${locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) ?: false}"
            )
        })


    val redirectAppSetting = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { it ->


            Log.i(
                "ammu",
                "TEST 2:  " + it.resultCode + " and  " + it.data.toString()
            )


        })

    DisposableEffect(Unit) {
        val receiver = GpsStatusReceiver { resiverInternt ->

            if (resiverInternt?.action == LocationManager.PROVIDERS_CHANGED_ACTION) {

                val locationManager =
                    context?.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
                isGpsEnabled.value =
                    locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) ?: false
                if (!isGpsEnabled.value) {
                    latitude = 0.00
                }
            } else if (resiverInternt?.action == ConnectivityManager.CONNECTIVITY_ACTION) {
                val connectivityManager =
                    context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetwork = connectivityManager.activeNetwork
                val isConnected = activeNetwork != null
                isInterNet.value = isConnected!!
            }
        }
        val intentFilter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        val intentFilter1 = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)

        context.registerReceiver(receiver, intentFilter)
        context.registerReceiver(receiver, intentFilter1)


        onDispose {
            context.unregisterReceiver(receiver)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Text(text = "GPS Status : ${if (isGpsEnabled.value) "ON" else "OFF"} and internet ${isInterNet.value}")
        Spacer(modifier = Modifier.padding(10.dp))

        Text(text = "late  : $latitude")

        Spacer(modifier = Modifier.padding(10.dp))

        Button(
            onClick = {
                if (isGpsEnabled.value) {


                    //check the network permission
                    if (ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {

                        locationPermissionLauncher.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                        )


                        var intent = Intent()
                        intent = intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", "dev.miyatech.buzzbee", null)
                        intent.setData(uri)
                        redirectAppSetting.launch(intent)

                    } else {
                        latitude = location.latitude
                    }
                } else {
                    settingResultRequest.launch(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }


            }
        ) {
            Text(text = "Allow")
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}


