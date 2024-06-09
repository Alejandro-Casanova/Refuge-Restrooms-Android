package com.example.refugerestrooms.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.refugerestrooms.R
import com.example.refugerestrooms.ui.theme.RefugeRestroomsTheme
import kotlin.math.round

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
    displayText: String = stringResource(R.string.fetching),
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        CircularProgressIndicator(
            modifier = Modifier,
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
        Text(
            text = displayText,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    displayIcon: Boolean = true,
    displayText: String = stringResource(R.string.loading_failed),
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(displayIcon) {
            Image(
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.ic_connection_error),
                contentDescription = ""
            )
        }
        Text(
            text = displayText,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
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
fun RestroomRatingBanner(
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

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    RefugeRestroomsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ErrorScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    RefugeRestroomsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            LoadingScreen()
        }
    }
}