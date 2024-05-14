package com.example.refugerestrooms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.refugerestrooms.ui.theme.RefugeRestroomsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RefugeRestroomsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AboutCardCompose()
                }
            }
        }
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
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp
        )
        Text(text = subtitle, fontStyle = FontStyle.Italic)
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
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(6.dp)
        )
        Text(text = "Code is on GitHub.\nContribute to the project on Patreon.",
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(6.dp)
        )
        Text(text = "This version was developed for Android by \nAlejandro Casanova.",
            fontSize = 14.sp,
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
                fontSize = 14.sp,
                modifier = Modifier.padding(0.dp)
            )
        }
    }
}
@Composable
fun AboutCardCompose(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
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
    }
}

@Preview(showBackground = true)
@Composable
fun AboutCardPreview() {
    RefugeRestroomsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AboutCardCompose()
        }
    }
}