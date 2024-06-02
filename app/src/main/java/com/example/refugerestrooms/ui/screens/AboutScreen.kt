package com.example.refugerestrooms.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.refugerestrooms.R
import com.example.refugerestrooms.ui.theme.RefugeRestroomsTheme

@Composable
fun AboutScreen(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.weight(1f))
        TitleCover(
            title = "Refuge Restrooms",
            subtitle = "An Open Source Initiative",
            imagePainter = painterResource(R.drawable.logo_lg),
            modifier = Modifier
        )
        Spacer(modifier = Modifier.weight(1f))
        AboutCard(
            modifier = Modifier
        )
        Spacer(modifier = Modifier.weight(0.10f))
    }
}

@Composable
fun TitleCover(title: String, subtitle: String, imagePainter: Painter, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        Image(
            imagePainter,
            modifier = Modifier
                //.fillMaxSize()
                .padding(bottom = 16.dp)
                .height(140.dp)
                .width(140.dp),
            contentDescription = "Refuge Restrooms Logo"
        )
        Text(
            text = title,
            style = MaterialTheme.typography.displayLarge
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.labelSmall
            //fontStyle = FontStyle.Italic
        )
    }
}

@Composable
fun ContactInfoItem(text: String, icon: ImageVector, modifier: Modifier = Modifier){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(2.dp)
    ){
        Icon(
            icon,
            contentDescription = null
        )
        Text(text = text, fontSize = 14.sp, modifier = Modifier.padding(start = 10.dp))
    }
}

@Composable
fun AboutCard(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(bottom = 8.dp)
    ) {
        Text(
            text = "Refuge Restrooms is Open Source.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(6.dp)
        )
        Text(text = "Code is on GitHub.\nContribute to the project on Patreon.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(6.dp)
        )
        Text(text = "This version was developed for Android by \nAlejandro Casanova.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(6.dp)
        )
        Row(
            modifier = Modifier.padding(6.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.copyleft_icon),
                contentDescription = null,
                modifier = Modifier
                    .rotate(0f)
                    .padding(end = 8.dp)
                    .width(14.dp)
            )
            Text(
                text = "copyleft 2024 refuge restrooms.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(0.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RefugeRestroomsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AboutScreen(
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreviewDark() {
    RefugeRestroomsTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AboutScreen(
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

