package com.example.animationentrylistitems

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animatedFloat
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.lazy.LazyColumnForIndexed

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.onActive
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.drawLayer
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.animationentrylistitems.ui.AnimationEntryListItemsTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimationEntryListItemsTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    App()
                }
            }
        }
    }
}

@Composable
fun App(){
    val listOfStuff = mutableListOf<String>()
    for(i in 1..100){
        listOfStuff.add("Item $i")
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(1f)
    ) {
        AnimatedList(listOfStuff)
    }
}

@Composable
fun AnimatedList(listOfStuff: List<String>){
    LazyColumnForIndexed(items = listOfStuff) { index, item ->
        AnimatedListItem(text = item, index = index)
    }
}

@Composable
fun AnimatedListItem(text: String, index: Int){
    val animationModifier = when(index){
        in 0..20 -> {
            // fade in effect
            val animationProgress = animatedFloat(0.0f)
            LaunchedEffect(subject = index, block = {
                animationProgress.animateTo(
                    1.0f,
                    anim = tween(1000)
                )
            })

            Modifier.padding(10.dp).alpha(animationProgress.value)
        }
        in 21..40 -> {
            // scale in effect
            val animationProgress = animatedFloat(0.8f)
            LaunchedEffect(subject = index, block = {
                animationProgress.animateTo(
                    1.0f,
                    anim = tween(1000, easing = LinearEasing)
                )
            })

            Modifier
                .padding(10.dp)
                .graphicsLayer(
                    scaleX = animationProgress.value,
                    scaleY = animationProgress.value
                )
        }
        in 41..60 -> {
            // Slide
            val animationProgress = animatedFloat(300f)
            LaunchedEffect(subject = index, block = {
                animationProgress.animateTo(
                    targetValue = 0f,
                    anim = tween(300, easing = FastOutSlowInEasing)
                )
            })

            Modifier.padding(10.dp).graphicsLayer(translationX = animationProgress.value)
        }
        else -> {
            // Rotate
            val animationProgress = animatedFloat(0f)
            LaunchedEffect(subject = index, block = {
                animationProgress.animateTo(
                    targetValue = 360f,
                    anim = tween(400, easing = FastOutSlowInEasing)
                )
            })

            Modifier.padding(10.dp).graphicsLayer(rotationX = animationProgress.value)
        }
    }


    Text(text, modifier = animationModifier
        .border(width = 1.dp, color = Color.Black)
        .padding(20.dp)
    )
}

