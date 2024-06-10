package com.example.refugerestrooms.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.refugerestrooms.R
import com.example.refugerestrooms.model.Restroom
import com.example.refugerestrooms.ui.ApiRequestState
import com.example.refugerestrooms.ui.RestroomsViewModel
import com.example.refugerestrooms.ui.theme.RefugeRestroomsTheme
import java.util.Locale
import kotlin.math.round

class ListViewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RefugeRestroomsTheme {
                RestroomListScreen(
                    modifier = Modifier
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RefugeTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
//                Image(
//                    modifier = Modifier
//                        .size(48.dp)
//                        .padding(8.dp),
//                    painter = painterResource(R.mipmap.ic_launcher_round),
//                    contentDescription = null
//                )
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.displayLarge
                )
            }
        },
        modifier = modifier
    )
}

@Composable
fun RestroomListScreen(
    modifier: Modifier = Modifier,
    restroomsViewModel: RestroomsViewModel,
    lazyListContentPadding : PaddingValues = PaddingValues(0.dp)
) {
    val appUiState by restroomsViewModel.uiState.collectAsState()
    when (val apiRequestState = restroomsViewModel.apiRequestState) {
        is ApiRequestState.Standby -> ErrorScreen(
            displayText = stringResource(R.string.no_search_query),
            modifier = modifier
                .fillMaxSize()
                .padding(lazyListContentPadding),
            drawableResource = R.drawable.info_error_red_transp_small
        )
        is ApiRequestState.Loading -> LoadingScreen(
            displayText = "Fetching Restrooms...",
            modifier = modifier
                .fillMaxSize()
                .padding(lazyListContentPadding)
        )
        is ApiRequestState.Success -> RestroomList(
            restroomsList = appUiState.restroomsList,
            modifier = modifier,
            contentPadding = lazyListContentPadding
        )
        is ApiRequestState.Error -> ErrorScreen(
            displayText = apiRequestState.errorMsg,
            modifier = modifier
                .fillMaxSize()
                .padding(lazyListContentPadding)
        )
    }
}

//@Composable
//private fun RestroomCardButton(
//    expanded: Boolean,
//    onClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    IconButton(
//        onClick = onClick,
//        modifier = modifier
//    ) {
//        Icon(
//            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
//            contentDescription = stringResource(R.string.expand_button_content_description),
//            tint = MaterialTheme.colorScheme.secondary
//        )
//    }
//
//}

@Composable
private fun RestroomRatingBanner(
    modifier: Modifier = Modifier,
    rating : Int? = R.integer.restroom_rating_1,
) {
    //val rating : Int? = if(ratingInt == null) null else integerResource(ratingInt)
    val message : String = when {
        rating == null -> "No Rating"
        else -> "${rating}% positive"
    }
    val color : Color = when{
        rating == null -> colorResource(R.color.purple_500)
        rating < 50 -> colorResource(R.color.red_rating)
        rating < 80 -> colorResource(R.color.orange_rating)
        else -> colorResource(R.color.green_rating)
    }
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = color,
        ),
        shape = RoundedCornerShape(12.dp),
        //border = BorderStroke(1.dp, Color.Black),
        modifier = modifier
            //.size(width = 240.dp, height = 100.dp)
    ) {
        Text(
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            text = message,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier
                .padding(8.dp),
            //style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
private fun RestroomCardMainBody(
    restroom: Restroom,
    modifier: Modifier = Modifier,
    nameNumLinesToDisplay : Int = 1,
    distanceNumLinesToDisplay: Int = 1,
    addressNumLinesToDisplay: Int = 1,
) {

    val distanceToRestroom = if(restroom.distance == null) {
        "DistanceUnknown"
    }else {
        String.format(Locale.ENGLISH, stringResource(R.string.miles_away), restroom.distance)
    }

    Column(
        modifier = modifier
    ) {
        Text(
            text = restroom.name,
            modifier = Modifier.padding(bottom = 4.dp, top = 4.dp),
            style = MaterialTheme.typography.displayMedium,
            overflow = TextOverflow.Ellipsis,
            maxLines = nameNumLinesToDisplay,
        )
        Text(
            overflow = TextOverflow.Ellipsis,
            maxLines = distanceNumLinesToDisplay,
            text = distanceToRestroom,
            modifier = Modifier,
            style = MaterialTheme.typography.labelSmall
        )
        Text(
            overflow = TextOverflow.Ellipsis,
            maxLines = addressNumLinesToDisplay,
            text = "${restroom.street}, ${restroom.city}, ${restroom.state}, ${restroom.country}",
            modifier = Modifier,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

fun calculateRating(upVotes: Int, downVotes: Int): Int? {
    val total = upVotes + downVotes
    val result: Int? = when{
        total == 0 -> null
        downVotes == 0 -> 100
        else -> round((upVotes.toFloat() / total) * 100.0f).toInt()
    }
    return result
}

@Composable
fun RestroomCardSecondaryBody(
    restroom: Restroom,
//    expanded: Boolean,
//    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.End,
        modifier = modifier
    ) {
        RestroomRatingBanner(
            rating = calculateRating(restroom.upVote, restroom.downVote)
        )
        // Optional Icons
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                //.fillMaxWidth()
                .padding(8.dp)
        ){
            // Plot Unisex Icon when necessary
            if(restroom.unisex){
                Image(
                    painter = painterResource(R.drawable.genderneutral),
                    contentDescription = "Unisex / Gender Neutral",
                    modifier = Modifier
                        .size(30.dp),
                    contentScale = ContentScale.Fit
                )
            }
            // Plot Accessible Icon when necessary
            if(restroom.accessible){
                Image(
                    painter = painterResource(R.drawable.accessiblerestroom),
                    contentDescription = "Accessible",
                    modifier = Modifier
                        .size(30.dp)
                        .padding(2.dp),
                    contentScale = ContentScale.Fit
                )
            }
            // Plot ChangingTable Icon when necessary
            if(restroom.changingTable){
                Image(
                    painter = painterResource(R.drawable.changing_room),
                    contentDescription = "Has Changing Room",
                    modifier = Modifier
                        .size(30.dp)
                        .padding(2.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }
//        RestroomCardButton(
//            expanded = expanded,
//            onClick = onClick,
//            modifier = Modifier.padding(0.dp)
//        )
    }
}

@Composable
fun RestroomCardExtraInfo(
    restroom: Restroom,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
    ) {
        if(restroom.directions != ""){
//            Text(
//                text = "Directions:",
//                style = MaterialTheme.typography.labelSmall
//            )
//            Text(
//                text = restroom.directions,
//                style = MaterialTheme.typography.bodyLarge
//            )
            Text(
                text = buildAnnotatedString {
                    append("Directions: ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                        append(restroom.directions)
                    }
                },
                style = MaterialTheme.typography.labelSmall
            )
        }
        if(restroom.comment != ""){
//            Text(
//                text = "Comments:",
//                style = MaterialTheme.typography.labelSmall
//            )
//            Text(
//                text = restroom.comment,
//                style = MaterialTheme.typography.bodyLarge
//            )
            Text(
                text = buildAnnotatedString {
                    append("Comments: ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                        append(restroom.comment)
                    }
                },
                style = MaterialTheme.typography.labelSmall
            )
        }
        if(restroom.upVote != 0){
            Text(
                text = buildAnnotatedString {
                    append("Up-Votes: ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                        append(restroom.upVote.toString())
                    }
                },
                style = MaterialTheme.typography.labelSmall
            )
        }
        if(restroom.downVote != 0){
            Text(
                text = buildAnnotatedString {
                    append("Down-Votes: ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                        append(restroom.downVote.toString())
                    }
                },
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestroomCard(restroom: Restroom, modifier: Modifier = Modifier) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    val textNumLines =
        if (expanded) Int.MAX_VALUE
        else 1

    ElevatedCard(
        onClick = { expanded = !expanded },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
                //.background(color = color)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                //            Image(
                //                painter = painterResource(R.drawable.toiletlogo),
                //                contentDescription = null,
                //                modifier = Modifier
                //                    .size(60.dp)
                //                    .padding(start = 8.dp, top = 8.dp),
                //                contentScale = ContentScale.Fit
                //            )
                RestroomCardMainBody(
                    restroom = restroom,
                    modifier = Modifier
                        .weight(1.5f)
                        .padding(start = 16.dp, bottom = 8.dp),
                    nameNumLinesToDisplay = textNumLines,
                    distanceNumLinesToDisplay = textNumLines,
                    addressNumLinesToDisplay = textNumLines
                )
                RestroomCardSecondaryBody(
                    restroom = restroom,
                    //                expanded = expanded,
                    //                onClick = { expanded = !expanded },
                    modifier = Modifier
                        //.weight(1.0f)
                        .padding(6.dp)
                )
            }
            if(expanded) {
                RestroomCardExtraInfo(
                    //dog.hobbies,
                    restroom = restroom,
                    modifier = Modifier.padding(
                        start = dimensionResource(R.dimen.padding_medium),
                        top = dimensionResource(R.dimen.padding_small),
                        end = dimensionResource(R.dimen.padding_medium),
                        bottom = dimensionResource(R.dimen.padding_medium)
                    )
                )
            }
        }
    }
}

@Composable
fun RestroomList(
    restroomsList: List<Restroom>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
//    Scaffold(
//        topBar = {
//            RefugeTopAppBar()
//        },
//        modifier = Modifier.fillMaxSize()
//    ) { contentPadding ->
        LazyColumn(
            contentPadding = contentPadding,
            modifier = modifier
        ) {
            items(restroomsList) { restroom ->
                RestroomCard(
                    restroom = restroom,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                )
            }
        }
    //}
}

@Preview(showBackground = true)
@Composable
fun ListViewPreview() {
    RefugeRestroomsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            RestroomListScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListViewPreviewDarkTheme() {
    RefugeRestroomsTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            RestroomListScreen()
        }
    }
}