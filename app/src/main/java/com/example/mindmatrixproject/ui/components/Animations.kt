package com.example.mindmatrixproject.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

/** Fades + slides up an item with a small per-index delay so lists/grids feel alive. */
@Composable
fun StaggeredItem(
    index: Int,
    delayPerItem: Long = 45L,
    content: @Composable () -> Unit,
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(index * delayPerItem)
        visible = true
    }
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(tween(320, easing = FastOutSlowInEasing)) +
            slideInVertically(
                animationSpec = tween(320, easing = FastOutSlowInEasing),
                initialOffsetY = { it / 6 },
            ),
    ) { content() }
}

object NavTransitions {
    private const val DURATION = 320

    fun slideEnter(): androidx.compose.animation.EnterTransition =
        slideInHorizontally(
            animationSpec = tween(DURATION, easing = FastOutSlowInEasing),
            initialOffsetX = { it / 2 },
        ) + fadeIn(tween(DURATION))

    fun slideExit(): androidx.compose.animation.ExitTransition =
        slideOutHorizontally(
            animationSpec = tween(DURATION, easing = FastOutSlowInEasing),
            targetOffsetX = { -it / 4 },
        ) + fadeOut(tween(DURATION))

    fun slidePopEnter(): androidx.compose.animation.EnterTransition =
        slideInHorizontally(
            animationSpec = tween(DURATION, easing = FastOutSlowInEasing),
            initialOffsetX = { -it / 4 },
        ) + fadeIn(tween(DURATION))

    fun slidePopExit(): androidx.compose.animation.ExitTransition =
        slideOutHorizontally(
            animationSpec = tween(DURATION, easing = FastOutSlowInEasing),
            targetOffsetX = { it / 2 },
        ) + fadeOut(tween(DURATION))

    fun fadeOnly(): androidx.compose.animation.EnterTransition =
        fadeIn(tween(220))

    fun fadeOutOnly(): androidx.compose.animation.ExitTransition =
        fadeOut(tween(220))
}