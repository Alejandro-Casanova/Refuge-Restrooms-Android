package com.example.refugerestrooms.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.refugerestrooms.R
import com.example.refugerestrooms.ui.RestroomsViewModel
import com.utsman.osmandcompose.CameraProperty
import com.utsman.osmandcompose.CameraState
import com.utsman.osmandcompose.DefaultMapProperties
import com.utsman.osmandcompose.Marker
import com.utsman.osmandcompose.MarkerState
import com.utsman.osmandcompose.OpenStreetMap
import com.utsman.osmandcompose.ZoomButtonVisibility
import com.utsman.osmandcompose.rememberOverlayManagerState
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.CopyrightOverlay


@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    restroomsViewModel: RestroomsViewModel,
) {
    val context = LocalContext.current
    val appUiState by restroomsViewModel.uiState.collectAsState()
    val restroomsList = appUiState.restroomsList
    val currentLocation = appUiState.currentLocation

    val cameraState = CameraState(
        CameraProperty(
            geoPoint = GeoPoint(
                currentLocation?.latitude ?: -6.3970066,
                currentLocation?.longitude ?: 106.8224316),
            zoom = 12.0
        )
    ) 

    val currentPositionMarkerState = MarkerState(
        geoPoint = GeoPoint(
            currentLocation?.latitude ?: -6.3970066,
            currentLocation?.longitude ?: 106.8224316),
        rotation = 90f
    )

    var mapProperties by remember {
        mutableStateOf(DefaultMapProperties)
    }

    val overlayManagerState = rememberOverlayManagerState()

    SideEffect {
        mapProperties = mapProperties
            .copy(isTilesScaledToDpi = true)
            .copy(tileSources = TileSourceFactory.DEFAULT_TILE_SOURCE)
            .copy(isEnableRotationGesture = true)
            .copy(zoomButtonVisibility = ZoomButtonVisibility.SHOW_AND_FADEOUT)
    }

    OpenStreetMap(
        modifier = modifier,
        cameraState = cameraState,
        properties = mapProperties,
        overlayManagerState = overlayManagerState,
        onFirstLoadListener = {
            val copyright = CopyrightOverlay(context)
            overlayManagerState.overlayManager.add(copyright)
        }
    ){
        // Current Position Marker
        Marker(
            state = currentPositionMarkerState,
            icon = context.getDrawable(R.drawable.current_position_small)
        )
        restroomsList.forEach { restroom ->
            Marker(
                state = MarkerState(
                    GeoPoint(restroom.latitude, restroom.longitude)
                ),
                icon = context.getDrawable(R.drawable.toilet_logo),
                title = restroom.name,
                snippet = restroom.directions
            ) {
                RestroomTooltipCard(
                    title = restroom.name,
                    address = "${restroom.street}, ${restroom.city}, ${restroom.state}, ${restroom.country}",

                )
            }
        }
    }

}

@Composable
fun RestroomTooltipCard(
    modifier: Modifier = Modifier,
    title: String,
    address: String,
) {
    ElevatedCard(
        modifier = modifier
        //.size(100.dp)
    ) {
        Column(
            modifier = Modifier
                //.fillMaxSize()
                .padding(12.dp),
//                            .background(
//                                color = MaterialTheme.colorScheme.surfaceTint,
//                                shape = RoundedCornerShape(7.dp)
//                            ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = title)
            Text(text = address)
        }
    }

}