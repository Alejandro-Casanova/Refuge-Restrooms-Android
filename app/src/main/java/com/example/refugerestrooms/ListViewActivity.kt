package com.example.refugerestrooms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.booleanResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.refugerestrooms.data.DummyDataSource
import com.example.refugerestrooms.model.Restroom
import com.example.refugerestrooms.ui.theme.RefugeRestroomsTheme

class ListViewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RefugeRestroomsTheme {
                RestroomListApp(
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
fun RestroomListApp(modifier: Modifier = Modifier) {
    RestroomList(
        restroomsList = DummyDataSource().loadDummyRestrooms(),
        modifier = modifier
    )
}

@Composable
fun RestroomCard(restroom: Restroom, modifier: Modifier = Modifier) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.toiletlogo),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .padding(start = 8.dp, top = 8.dp),
                contentScale = ContentScale.Fit
            )
            Column(
                modifier = Modifier
                    .weight(1.5f)
                    .padding(start = 8.dp, bottom = 8.dp)
            ) {
                Text(
                    text = stringResource(restroom.nameStringResourceId),
                    modifier = Modifier.padding(bottom = 4.dp, top = 4.dp),
                    style = MaterialTheme.typography.displayMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
                Text(
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    text = stringResource(restroom.addressStringResourceId),
                    modifier = Modifier,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Column(
                modifier = Modifier
                    .weight(1.0f)
                    .padding(6.dp)
            ) {
                Text(
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    text = stringResource(restroom.distanceStringResourceId),
                    modifier = Modifier,
                    style = MaterialTheme.typography.labelSmall
                )
                // Optional Icons
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                ){
                    // Plot Unisex Icon when necessary
                    if(booleanResource(restroom.unisexFlagResourceId)){
                        Image(
                            painter = painterResource(R.drawable.genderneutral),
                            contentDescription = "Unisex / Gender Neutral",
                            modifier = Modifier
                                .size(30.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                    // Plot Accessible Icon when necessary
                    if(booleanResource(restroom.accessibleFlagResourceId)){
                        Image(
                            painter = painterResource(R.drawable.accessiblerestroom),
                            contentDescription = "Accessible",
                            modifier = Modifier
                                .size(30.dp)
                                .padding(2.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RestroomList(restroomsList: List<Restroom>, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            RefugeTopAppBar()
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = modifier
        ) {
            items(restroomsList) { restroom ->
                RestroomCard(
                    restroom = restroom,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListViewPreview() {
    RefugeRestroomsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            RestroomListApp()
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
            RestroomListApp()
        }
    }
}