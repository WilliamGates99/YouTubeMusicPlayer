package com.xeniac.youtubemusicplayer.core.ui.components

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color

@Composable
fun Modifier.pulseEffect(
    initialSize: Float,
    targetSize: Float,
    pulseColor: Color,
    initialOpacity: Float = 1f,
    targetOpacity: Float = 0f,
    startDelayInMs: Int = 0,
    animationSpec: InfiniteRepeatableSpec<Float> = InfiniteRepeatableSpec(
        animation = tween(durationMillis = 4000),
        repeatMode = RepeatMode.Restart,
        initialStartOffset = StartOffset(offsetMillis = startDelayInMs)
    )
): Modifier = composed {
    val infiniteTransition = rememberInfiniteTransition(label = "infiniteTransition")

    val pulseRadius by infiniteTransition.animateFloat(
        initialValue = initialSize,
        targetValue = targetSize,
        animationSpec = animationSpec,
        label = "pulseRadius"
    )

    val pulseOpacity by infiniteTransition.animateFloat(
        initialValue = initialOpacity,
        targetValue = targetOpacity,
        animationSpec = animationSpec,
        label = "pulseOpacity"
    )

    drawBehind {
        drawCircle(
            color = pulseColor,
            radius = pulseRadius,
            alpha = pulseOpacity
        )
    }
}