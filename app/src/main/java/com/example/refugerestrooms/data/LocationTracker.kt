package com.example.refugerestrooms.data

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine

interface LocationTracker {
    suspend fun getLastLocation(): Location?
    fun getCurrentLocation(
        onGetCurrentLocationSuccess: (Location) -> Unit,
        onGetCurrentLocationFailed: (Exception) -> Unit,
        priority: Boolean = true
    )
}

class DefaultLocationTracker(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val application: Application,
) : LocationTracker {

//    private fun getCurrentLocation(
//        onGetCurrentLocationSuccess: (Pair<Double, Double>) -> Unit,
//        onGetCurrentLocationFailed: (Exception) -> Unit,
//        priority: Boolean = true
//    ) {
//        // Determine the accuracy priority based on the 'priority' parameter
//        val accuracy = if (priority) Priority.PRIORITY_HIGH_ACCURACY
//        else Priority.PRIORITY_BALANCED_POWER_ACCURACY
//
//        // Check if location permissions are granted
//        if (areLocationPermissionsGranted()) {
//            // Retrieve the current location asynchronously
//            fusedLocationProviderClient.getCurrentLocation(
//                accuracy, CancellationTokenSource().token,
//            ).addOnSuccessListener { location ->
//                location?.let {
//                    // If location is not null, invoke the success callback with latitude and longitude
//                    onGetCurrentLocationSuccess(Pair(it.latitude, it.longitude))
//                }?.run {
//                    //Location null do something
//                }
//            }.addOnFailureListener { exception ->
//                // If an error occurs, invoke the failure callback with the exception
//                onGetCurrentLocationFailed(exception)
//            }
//        }
//    }


    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getLastLocation(): Location? {
        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
            application,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            application,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val locationManager = application.getSystemService(
            Context.LOCATION_SERVICE
        ) as LocationManager

        val isGpsEnabled = locationManager
            .isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (!isGpsEnabled && !(hasAccessCoarseLocationPermission || hasAccessFineLocationPermission)) {
            return null
        }

        // Request fetch new location
        fusedLocationProviderClient.getCurrentLocation(
            Priority.PRIORITY_BALANCED_POWER_ACCURACY, //PRIORITY_HIGH_ACCURACY,
            CancellationTokenSource().token,
        )

        return suspendCancellableCoroutine { cont ->
            fusedLocationProviderClient.lastLocation.apply {
                if (isComplete) {
                    if (isSuccessful) {
                        cont.resume(result) {} // Resume coroutine with location result
                    } else {
                        cont.resume(null) {} // Resume coroutine with null location result
                    }
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener {
                    cont.resume(it) {}  // Resume coroutine with location result
                }
                addOnFailureListener {
                    cont.resume(null) {} // Resume coroutine with null location result
                }
                addOnCanceledListener {
                    cont.cancel() // Cancel the coroutine
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getCurrentLocation(
        onGetCurrentLocationSuccess: (Location) -> Unit,
        onGetCurrentLocationFailed: (Exception) -> Unit,
        priority: Boolean
    ) {
        // Determine the accuracy priority based on the 'priority' parameter
        val accuracy = if (priority) Priority.PRIORITY_HIGH_ACCURACY
        else Priority.PRIORITY_BALANCED_POWER_ACCURACY

        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
            application,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            application,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val locationManager = application.getSystemService(
            Context.LOCATION_SERVICE
        ) as LocationManager

        val isGpsEnabled = locationManager
            .isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (!(hasAccessCoarseLocationPermission || hasAccessFineLocationPermission)) {
            Log.d("refugeDebug", "getCurrentLocationNow: A")
            onGetCurrentLocationFailed(Exception("No location access permission has been granted to this app! Please address this in settings."))
        }else if(!isGpsEnabled) {
            Log.d("refugeDebug", "getCurrentLocationNow: B")
            onGetCurrentLocationFailed(Exception("GPS is disabled on this device! Please enable it and try again."))
        }else {
            // Retrieve the current location asynchronously
            Log.d("refugeDebug", "getCurrentLocationNow: C")
            fusedLocationProviderClient.getCurrentLocation(
                accuracy, CancellationTokenSource().token,
            ).addOnSuccessListener { location ->
                location?.let {
                    // If location is not null, invoke the success callback with latitude and longitude
                    Log.d("refugeDebug", "getCurrentLocationNow: D")
                    onGetCurrentLocationSuccess(it)//(Pair(it.latitude, it.longitude))
                }?.run {
                    Log.d("refugeDebug", "getCurrentLocationNow: F")
                    //Location null do something
                }
            }.addOnFailureListener { exception ->
                // If an error occurs, invoke the failure callback with the exception
                onGetCurrentLocationFailed(exception)
                Log.d("refugeDebug", "getCurrentLocationNow: G")
            }
        }
    }

}