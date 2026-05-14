package com.example.mindmatrixproject.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindmatrixproject.data.ToyRepository
import com.example.mindmatrixproject.ui.components.ColorDot

private const val PLAY_VIDEO_URL =
    "https://www.youtube.com/results?search_query=Channapatna+toys+making"

@Composable
fun ToyDetailScreen(
    toyId: String,
    onBack: () -> Unit,
    onArtisanClick: (String) -> Unit,
    onCoopClick: (String) -> Unit,
) {
    val toy = remember(toyId) { ToyRepository.toyById(toyId) } ?: return
    val artisan = remember(toy.artisanId) { ToyRepository.artisanById(toy.artisanId) } ?: return
    val coop = remember(artisan.cooperativeId) { ToyRepository.cooperativeById(artisan.cooperativeId) } ?: return
    val accent = Color(toy.accentHex)
    val coopAccent = Color(coop.accentHex)
    val uriHandler = LocalUriHandler.current
    var entered by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { entered = true }
    val emojiScale by animateFloatAsState(
        targetValue = if (entered) 1f else 0.6f,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = "emojiScale",
    )

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState()),
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .background(
                        Brush.verticalGradient(listOf(accent, accent.copy(alpha = 0.6f)))
                    ),
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .padding(12.dp)
                        .background(Color.White.copy(alpha = 0.22f), CircleShape),
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                    )
                }
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(toy.emoji, fontSize = (130 * emojiScale).sp)
                }
                Box(
                    Modifier
                        .padding(20.dp)
                        .align(Alignment.BottomStart)
                        .background(Color.White.copy(alpha = 0.22f), RoundedCornerShape(50))
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Verified,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(14.dp),
                        )
                        Spacer(Modifier.size(6.dp))
                        Text(
                            "GI tagged · ID ${toy.id}",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
            }

            Column(Modifier.padding(20.dp)) {
                Text(
                    toy.category,
                    style = MaterialTheme.typography.labelLarge,
                    color = accent,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    toy.name,
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.ExtraBold,
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    toy.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(Modifier.height(14.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "₹${toy.priceInr}",
                        style = MaterialTheme.typography.displayLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.ExtraBold,
                    )
                    Spacer(Modifier.weight(1f))
                    Box(
                        Modifier
                            .size(8.dp)
                            .background(Color(0xFF3B9A8E), CircleShape),
                    )
                    Spacer(Modifier.size(6.dp))
                    Text(
                        "Verified",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color(0xFF3B9A8E),
                        fontWeight = FontWeight.Bold,
                    )
                }

                Spacer(Modifier.height(16.dp))
                Text(
                    "Lac dyes",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    toy.lacColors.forEach { c -> ColorDot(colorForLabel(c), c) }
                }

                Spacer(Modifier.height(20.dp))
                LinkCard(
                    title = "Made by",
                    name = artisan.name,
                    subtitle = "${artisan.yearsOfCraft} years of craft · ${artisan.village.substringBefore(',')}",
                    emoji = artisan.photoEmoji,
                    accent = coopAccent,
                    onClick = { onArtisanClick(artisan.id) },
                )

                Spacer(Modifier.height(12.dp))
                LinkCard(
                    title = "Cooperative",
                    name = coop.name,
                    subtitle = coop.locality,
                    emoji = coop.emoji,
                    accent = coopAccent,
                    onClick = { onCoopClick(coop.id) },
                )

                Spacer(Modifier.height(20.dp))
                WatchVideoButton(title = artisan.videoTitle) {
                    uriHandler.openUri(PLAY_VIDEO_URL)
                }

                Spacer(Modifier.height(20.dp))
            }
        }
    }
}

@Composable
private fun LinkCard(
    title: String,
    name: String,
    subtitle: String,
    emoji: String,
    accent: Color,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                Modifier
                    .size(54.dp)
                    .background(accent.copy(alpha = 0.18f), CircleShape),
                contentAlignment = Alignment.Center,
            ) { Text(emoji, fontSize = 28.sp) }
            Spacer(Modifier.size(14.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    title,
                    style = MaterialTheme.typography.labelMedium,
                    color = accent,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    name,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Icon(
                Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = accent,
                modifier = Modifier.size(20.dp),
            )
        }
    }
}

@Composable
private fun WatchVideoButton(title: String, onClick: () -> Unit) {
    val gradient = Brush.linearGradient(listOf(Color(0xFF1D7874), Color(0xFF2A9D8F)))
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .background(gradient)
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                Modifier
                    .size(52.dp)
                    .background(Color.White.copy(alpha = 0.22f), CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Text("▶", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.size(14.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    "Watch how it's made",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White.copy(alpha = 0.9f),
                )
                Text(
                    title,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

private fun colorForLabel(label: String): Color = when (label.lowercase()) {
    "crimson", "red", "brick red" -> Color(0xFFD64545)
    "gold", "mustard", "yellow", "turmeric" -> Color(0xFFEFC368)
    "green", "mint" -> Color(0xFF3B9A8E)
    "indigo", "blue" -> Color(0xFF4A7B9D)
    "pink", "pastel pink" -> Color(0xFFD68B95)
    "cream", "natural" -> Color(0xFFE8DCC4)
    "brown" -> Color(0xFF8B5A3C)
    else -> Color(0xFFB0BEC5)
}