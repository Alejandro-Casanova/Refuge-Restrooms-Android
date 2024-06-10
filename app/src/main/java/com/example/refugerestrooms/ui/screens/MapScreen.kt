package com.example.refugerestrooms.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.refugerestrooms.R
import com.example.refugerestrooms.ui.RestroomsViewModel
import com.example.refugerestrooms.ui.theme.RefugeRestroomsTheme
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
import java.util.Locale


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
        // Restroom Markers
        restroomsList.forEach { restroom ->

            // Format "distance to restroom" string
            val distanceToRestroom = if(restroom.distance == null) {
                "DistanceUnknown"
            }else {
                String.format(Locale.ENGLISH, stringResource(R.string.miles_away), restroom.distance)
            }

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
                    rating = calculateRating(upVotes = restroom.upVote, downVotes = restroom.downVote),
                    distance = distanceToRestroom
                )
            }
        }
        // Current Position Marker
        Marker(
            state = currentPositionMarkerState,
            icon = context.getDrawable(R.drawable.current_position_small)
        )
    }

}

@Composable
fun RestroomTooltipCard(
    modifier: Modifier = Modifier,
    title: String,
    address: String,
    distance: String,
    rating: Int?
) {
    ElevatedCard(
        modifier = modifier
        //.size(100.dp)
    ) {
        Column(
            modifier = Modifier
                //.fillMaxSize()
                .padding(12.dp)
                .width(200.dp),
//                            .background(
//                                color = MaterialTheme.colorScheme.surfaceTint,
//                                shape = RoundedCornerShape(7.dp)
//                            ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(
                text = distance,
                style = MaterialTheme.typography.labelSmall,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(
                text = address,
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            RestroomRatingBanner(rating = rating)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun RestroomTooltipPreview() {
    RefugeRestroomsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            RestroomTooltipCard(
                title = "PlaceHolder Title",
                address = "PlaceHolder Address",
                rating = 75,
                distance = "Placeholder Distance"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RestroomTooltipPreviewDark() {
    RefugeRestroomsTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            RestroomTooltipCard(
                title = "PlaceHolder Title",
                address = "PlaceHolder Address",
                rating = 75,
                distance = "Placeholder Distance"
            )
        }
    }
}