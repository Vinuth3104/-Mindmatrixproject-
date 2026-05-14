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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Park
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindmatrixproject.data.Toy
import com.example.mindmatrixproject.data.ToyRepository
import com.example.mindmatrixproject.ui.components.StaggeredItem

@Composable
fun HomeScreen(
    onVerify: () -> Unit,
    onHowMade: () -> Unit,
    onMakers: () -> Unit,
    onCatalog: () -> Unit,
    onToyClick: (String) -> Unit,
    onArtisanClick: (String) -> Unit,
) {
    val toys = remember { ToyRepository.allToys() }
    val artisans = remember { ToyRepository.allArtisans() }
    val (totalToys, totalCoops, totalArtisans) = remember { ToyRepository.stats() }

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(top = 8.dp, bottom = 24.dp),
    ) {
        StaggeredItem(0) {
            HeaderRow()
        }
        StaggeredItem(1) {
            Box(Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
                HeroCard(onCta = onVerify)
            }
        }
        StaggeredItem(2) {
            StatsRow(totalToys, totalCoops, totalArtisans)
        }
        StaggeredItem(3) {
            SectionTitle(title = "Featured toys", action = "See all", onAction = onCatalog)
        }
        StaggeredItem(4) {
            FeaturedToysCarousel(toys = toys, onToyClick = onToyClick)
        }
        StaggeredItem(5) {
            SectionTitle(title = "Quick actions")
        }
        StaggeredItem(6) {
            QuickActionsRow(
                onHowMade = onHowMade,
                onMakers = onMakers,
                onCatalog = onCatalog,
            )
        }
        StaggeredItem(7) {
            SectionTitle(title = "Meet the makers", action = "View all", onAction = onMakers)
        }
        StaggeredItem(8) {
            MakersStrip(
                artisans = artisans,
                onArtisanClick = onArtisanClick,
            )
        }
        StaggeredItem(9) {
            HeritageFooter()
        }
    }
}

@Composable
private fun HeaderRow() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            Modifier
                .size(40.dp)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.16f), CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                Icons.Default.AutoAwesome,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
            )
        }
        Spacer(Modifier.width(10.dp))
        Column(Modifier.weight(1f)) {
            Text(
                "Channapatna Namma",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
            )
            Text(
                "Authentic toys, told by their makers",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun HeroCard(onCta: () -> Unit) {
    val gradient = Brush.linearGradient(
        listOf(Color(0xFFE63946), Color(0xFFF4A261), Color(0xFFFFD166))
    )
    Card(
        onClick = onCta,
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(168.dp)
                .background(gradient)
                .padding(22.dp),
        ) {
            Column(Modifier.fillMaxSize()) {
                Box(
                    Modifier
                        .background(Color.White.copy(alpha = 0.22f), RoundedCornerShape(50))
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                ) {
                    Text(
                        "GI tagged · Karnataka",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
                Spacer(Modifier.weight(1f))
                Text(
                    "Verify a toy",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                )
                Text(
                    "Tap here to scan or enter a 6-digit ID",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.95f),
                )
                Spacer(Modifier.height(10.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        Modifier
                            .background(Color.White, RoundedCornerShape(50))
                            .padding(horizontal = 14.dp, vertical = 8.dp),
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.QrCodeScanner,
                                contentDescription = null,
                                tint = Color(0xFFE63946),
                                modifier = Modifier.size(16.dp),
                            )
                            Spacer(Modifier.width(6.dp))
                            Text(
                                "Start verification",
                                style = MaterialTheme.typography.labelLarge,
                                color = Color(0xFFE63946),
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                    Spacer(Modifier.weight(1f))
                    Text("🎠", fontSize = 36.sp)
                }
            }
        }
    }
}

@Composable
private fun StatsRow(toys: Int, coops: Int, artisans: Int) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        StatCard("$toys", "verified toys", Color(0xFFD64545), Modifier.weight(1f))
        StatCard("$coops", "cooperatives", Color(0xFF3B9A8E), Modifier.weight(1f))
        StatCard("$artisans", "artisans", Color(0xFF4A7B9D), Modifier.weight(1f))
    }
}

@Composable
private fun StatCard(value: String, label: String, accent: Color, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = modifier,
    ) {
        Column(Modifier.padding(14.dp)) {
            Box(
                Modifier
                    .size(8.dp)
                    .background(accent, CircleShape)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                value,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.ExtraBold,
            )
            Text(
                label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun SectionTitle(title: String, action: String? = null, onAction: (() -> Unit)? = null) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f),
        )
        if (action != null && onAction != null) {
            Row(
                Modifier
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(50))
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    action,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(end = 4.dp),
                )
                Icon(
                    Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(14.dp),
                )
            }
        }
    }
}

@Composable
private fun FeaturedToysCarousel(toys: List<Toy>, onToyClick: (String) -> Unit) {
    val state = rememberLazyListState()
    LazyRow(
        state = state,
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        items(toys) { toy -> CarouselToyCard(toy, onClick = { onToyClick(toy.id) }) }
    }
}

@Composable
private fun CarouselToyCard(toy: Toy, onClick: () -> Unit) {
    val accent = Color(toy.accentHex)
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.width(180.dp),
    ) {
        Column {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .background(
                        Brush.linearGradient(listOf(accent, accent.copy(alpha = 0.7f)))
                    ),
                contentAlignment = Alignment.Center,
            ) { Text(toy.emoji, fontSize = 52.sp) }
            Column(Modifier.padding(12.dp)) {
                Text(
                    toy.category,
                    style = MaterialTheme.typography.labelMedium,
                    color = accent,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    toy.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    "₹${toy.priceInr}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Composable
private fun QuickActionsRow(
    onHowMade: () -> Unit,
    onMakers: () -> Unit,
    onCatalog: () -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        QuickAction(
            "How it's made", "🪵", Color(0xFF3B9A8E), Icons.Default.Park,
            onClick = onHowMade, modifier = Modifier.weight(1f),
        )
        QuickAction(
            "Makers map", "🗺️", Color(0xFF4A7B9D), Icons.Default.LocationOn,
            onClick = onMakers, modifier = Modifier.weight(1f),
        )
        QuickAction(
            "Browse all", "🧸", Color(0xFFE07A8B), Icons.Default.Storefront,
            onClick = onCatalog, modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun QuickAction(
    title: String,
    emoji: String,
    accent: Color,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = modifier.height(120.dp),
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(listOf(accent, accent.copy(alpha = 0.75f)))
                )
                .padding(14.dp),
        ) {
            Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.Top) {
                    Box(
                        Modifier
                            .size(34.dp)
                            .background(Color.White.copy(alpha = 0.22f), RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center,
                    ) { Icon(icon, contentDescription = null, tint = Color.White) }
                    Spacer(Modifier.weight(1f))
                    Text(emoji, fontSize = 22.sp)
                }
                Text(
                    title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                )
            }
        }
    }
}

@Composable
private fun MakersStrip(
    artisans: List<com.example.mindmatrixproject.data.Artisan>,
    onArtisanClick: (String) -> Unit,
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        items(artisans) { artisan ->
            val coop = remember(artisan.id) {
                ToyRepository.cooperativeById(artisan.cooperativeId)
            }
            val accent = Color(coop?.accentHex ?: 0xFFD64545)
            Card(
                onClick = { onArtisanClick(artisan.id) },
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                modifier = Modifier.width(160.dp),
            ) {
                Column(Modifier.padding(14.dp)) {
                    Box(
                        Modifier
                            .size(54.dp)
                            .background(accent.copy(alpha = 0.2f), CircleShape),
                        contentAlignment = Alignment.Center,
                    ) { Text(artisan.photoEmoji, fontSize = 28.sp) }
                    Spacer(Modifier.height(8.dp))
                    Text(
                        artisan.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                    )
                    Text(
                        "${artisan.yearsOfCraft} yrs · ${artisan.village.substringBefore(',')}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                    )
                }
            }
        }
    }
}

@Composable
private fun HeritageFooter() {
    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
    ) {
        Column(Modifier.padding(20.dp)) {
            Text(
                "200+ years of craft",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
            )
            Spacer(Modifier.height(6.dp))
            Text(
                "Channapatna toys are GI-tagged. Every authentic piece is hand-turned, lac-dyed, and stamped with a 6-digit ID — verifying it here helps an artisan, not a counterfeiter.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
