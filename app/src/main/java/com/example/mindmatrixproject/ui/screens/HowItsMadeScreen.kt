package com.example.mindmatrixproject.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindmatrixproject.R
import com.example.mindmatrixproject.ui.components.StaggeredItem

private data class Step(val n: Int, val title: String, val emoji: String, val body: String, val accent: Color)

private const val PROCESS_VIDEO_URL =
    "https://www.youtube.com/results?search_query=Channapatna+toys+making+process"

@Composable
fun HowItsMadeScreen(onBack: () -> Unit) {
    val steps = remember {
        listOf(
            Step(1, "Choose Hale Wood", "🌳",
                "Soft, ivory-white Wrightia tinctoria (Hale mara). Sustainably harvested from Karnataka forests — light, smooth, and easy to turn.",
                Color(0xFF3B9A8E)),
            Step(2, "Season & Cure", "☀️",
                "Logs are sun-dried for weeks to remove moisture so the toy will not crack.",
                Color(0xFFE89B5C)),
            Step(3, "Hand-turn on Lathe", "🪚",
                "The artisan shapes the toy on a hand-driven or electric lathe. Each curve is judged by eye.",
                Color(0xFF4A7B9D)),
            Step(4, "Apply Lac", "🌈",
                "A stick of natural lac (resin from the Kerria lacca insect) is held against the spinning wood. Friction melts it into a glossy coat.",
                Color(0xFFD64545)),
            Step(5, "Natural Dyes", "🌿",
                "Colour comes from plants — turmeric (yellow), indigo (blue), bark (brown). 100% non-toxic, safe for babies.",
                Color(0xFFEFC368)),
            Step(6, "Polish with Kewda Leaf", "🍃",
                "A dried screw-pine leaf is pressed onto the spinning toy — natural fibres polish the surface to a soft shine.",
                Color(0xFF2A7A75)),
            Step(7, "Quality Check & GI Tag", "🏷️",
                "Each toy is inspected and stamped with a 6-digit Channapatna GI authenticity code.",
                Color(0xFFD68B95)),
        )
    }
    val uri = LocalUriHandler.current

    LazyColumn(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item {
            StaggeredItem(0) { TopRow(onBack) }
        }
        item {
            StaggeredItem(1) { IntroBlock() }
        }
        item {
            StaggeredItem(2) { VideoButton { uri.openUri(PROCESS_VIDEO_URL) } }
        }
        itemsIndexed(steps, key = { _, s -> s.n }) { idx, step ->
            StaggeredItem(idx + 3) { StepCard(step) }
        }
        item {
            StaggeredItem(steps.size + 3) { FactsCard() }
        }
        item { Spacer(Modifier.height(20.dp)) }
    }
}

@Composable
private fun TopRow(onBack: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(
            onClick = onBack,
            modifier = Modifier
                .size(40.dp)
                .background(MaterialTheme.colorScheme.surface, CircleShape),
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@Composable
private fun IntroBlock() {
    Column {
        Text(
            "From forest to playroom",
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            stringResource(R.string.how_made_intro),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun VideoButton(onClick: () -> Unit) {
    val gradient = Brush.linearGradient(
        listOf(Color(0xFF1D7874), Color(0xFF2A9D8F))
    )
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
                    .size(58.dp)
                    .background(Color.White.copy(alpha = 0.22f), CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Text("▶", color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.size(14.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    "Watch the documentary",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    "Opens YouTube · 8 min",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.85f),
                )
            }
        }
    }
}

@Composable
private fun StepCard(step: Step) {
    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(Modifier.padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier
                    .size(64.dp)
                    .background(
                        Brush.linearGradient(
                            listOf(step.accent, step.accent.copy(alpha = 0.7f))
                        ),
                        RoundedCornerShape(18.dp),
                    ),
                contentAlignment = Alignment.Center,
            ) { Text(step.emoji, fontSize = 32.sp) }
            Spacer(Modifier.size(14.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    "Step ${step.n}",
                    style = MaterialTheme.typography.labelMedium,
                    color = step.accent,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    step.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(Modifier.size(4.dp))
                Text(
                    step.body,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun FactsCard() {
    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.18f),
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(Modifier.padding(20.dp)) {
            Text(
                "Did you know?",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
            )
            Spacer(Modifier.height(8.dp))
            listOf(
                "GI tagged in 2005 — Geographical Indication of Karnataka",
                "Over 200 years old craft tradition",
                "100% non-toxic, child-safe lac dyes",
                "Replaces ~10kg of plastic per family per year",
            ).forEach { fact ->
                Row(Modifier.fillMaxWidth().padding(vertical = 3.dp)) {
                    Text("•  ", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(
                        fact,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }
}